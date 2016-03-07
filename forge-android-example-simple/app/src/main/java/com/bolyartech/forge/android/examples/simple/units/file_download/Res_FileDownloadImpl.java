package com.bolyartech.forge.android.examples.simple.units.file_download;

import android.os.Environment;

import com.bolyartech.forge.android.examples.simple.app.BusResidentComponent;
import com.bolyartech.forge.base.http.ProgressResponseBody;
import com.squareup.otto.Bus;

import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;


/**
 * Created by ogre on 2015-11-08 18:46
 */
public class Res_FileDownloadImpl extends BusResidentComponent implements Res_FileDownload {
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass()
            .getSimpleName());

    private static final String FILE_TO_DOWNLOAD = "NKPD-2011_1-928.pdf";

    private State mState = State.IDLE;

    private String mDownloadedFilePath;

    private final String mBaseUrl;
    private final OkHttpClient mOkHttpClient;
    private Call mCall;


    private ProgressResponseBody.Listener mListener = new ProgressResponseBody.Listener() {
        @Override
        public void update(long bytesRead, long contentLength, boolean done) {
            Act_FileDownload act = (Act_FileDownload) getActivity();
            if (act != null) {
                act.onProgress(bytesRead / (contentLength / 100f));
            }
        }
    };


    public Res_FileDownloadImpl(Bus bus, String baseUrl, OkHttpClient okHttpClient) {
        super(bus);
        mBaseUrl = baseUrl;
        mOkHttpClient = okHttpClient;
    }


    @Override
    public State getState() {
        return mState;
    }


    @Override
    public synchronized void download() {
        if (mState == State.IDLE) {
            mState = State.DOWNLOADING;

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient tmpHttp = mOkHttpClient.newBuilder().addNetworkInterceptor(new Interceptor() {
                        @Override public Response intercept(Chain chain) throws IOException {
                            Response originalResponse = chain.proceed(chain.request());
                            return originalResponse.newBuilder()
                                    .body(new ProgressResponseBody(originalResponse.body(), mListener))
                                    .build();
                        }
                    }).build();

                    Request req = new Request.Builder().get().url(mBaseUrl + FILE_TO_DOWNLOAD).build();
                    File dlDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File file = new File(dlDir, FILE_TO_DOWNLOAD);

                    // Clean up if not first download
                    if (file.exists()) {
                        file.delete();
                    }
                    try {
                        mCall = tmpHttp.newCall(req);
                        Response response = mCall.execute();
                        BufferedSink sink = Okio.buffer(Okio.sink(file));
                        sink.writeAll(response.body().source());
                        sink.close();

                        mDownloadedFilePath = file.getAbsolutePath();
                        downloadOk();
                    } catch (IOException e) {
                        if (!mCall.isCanceled()) {
                            mLogger.error("Download failed: {}", e);
                            downloadFailed();
                        }
                    }
                }
            });
            t.start();
        } else {
            throw new IllegalStateException("Not in IDLE state");
        }
    }


    @Override
    public synchronized void reset() {
        mState = State.IDLE;
    }


    @Override
    public String getDownloadedFilePath() {
        return mDownloadedFilePath;
    }


    @Override
    public synchronized void abortDownload() {
        if (mState == State.DOWNLOADING) {
            if (mCall != null) {
                mCall.cancel();
            }
        }
    }


    private synchronized void downloadOk() {
        mState = State.DOWNLOAD_OK;

        final Act_FileDownload act = (Act_FileDownload) getActivity();
        if (act != null) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    act.onDownloadOk();
                }
            });
        }
    }


    private synchronized void downloadFailed() {
        mState = State.DOWNLOAD_FAIL;

        final Act_FileDownload act = (Act_FileDownload) getActivity();
        if (act != null) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    act.onDownloadFailed();
                }
            });
        }
    }
}
