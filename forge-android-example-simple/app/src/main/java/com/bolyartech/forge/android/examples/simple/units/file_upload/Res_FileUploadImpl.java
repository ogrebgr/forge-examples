package com.bolyartech.forge.android.examples.simple.units.file_upload;

import com.bolyartech.forge.android.examples.simple.app.BusResidentComponent;
import com.bolyartech.forge.android.examples.simple.misc.ForgeGsonResultProducer;
import com.bolyartech.forge.base.exchange.ForgeExchangeResult;
import com.bolyartech.forge.base.exchange.ResultProducer;
import com.bolyartech.forge.base.http.CountingFileRequestBody;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by ogre on 2015-11-05 10:18
 */
public class Res_FileUploadImpl extends BusResidentComponent implements Res_FileUpload {
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass()
            .getSimpleName());

    private State mState = State.IDLE;

    private long mLastResult;

    private final String mBaseUrl;

    private final OkHttpClient mOkHttpClient;

    private float mFileSizeOnePercent;

    private Call mCall;


    private CountingFileRequestBody.ProgressListener mProgressListener = new CountingFileRequestBody.ProgressListener() {
        @Override
        public void transferredSoFar(final long num) {
            final Act_FileUpload act = (Act_FileUpload) getActivity();
            if (act != null) {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        act.onProgress(num / mFileSizeOnePercent);
                    }
                });
            }
        }
    };


    public Res_FileUploadImpl(Bus bus, String baseUrl, OkHttpClient okHttpClient) {
        super(bus);
        mBaseUrl = baseUrl;
        mOkHttpClient = okHttpClient;
    }


    @Override
    public State getState() {
        return mState;
    }


    @Override
    public synchronized void upload(final File file, final MediaType mt) {
        if (mState == State.IDLE) {
            mState = State.UPLOADING;
            mFileSizeOnePercent = file.length() / 100f;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestBody fileBody = new CountingFileRequestBody(file, mt, mProgressListener);

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file_upload", file.getName(), fileBody)
                            .build();


                    Request request = new Request.Builder().url(mBaseUrl + "upload.php")
                            .addHeader("Content-Type", "multipart/form-data")
                            .post(requestBody)
                            .build();


                    mCall = mOkHttpClient.newCall(request);
                    try {
                        Response response = mCall.execute();

                        if (response.isSuccessful()) {
                            ForgeGsonResultProducer rp = new ForgeGsonResultProducer();
                            ForgeExchangeResult forgeExchangeResult;
                            try {
                                forgeExchangeResult = rp.produce(response);
                                try {
                                    JSONObject jobj = new JSONObject(forgeExchangeResult.getPayload());
                                    mLastResult = jobj.getLong("file_size");
                                    uploadOk();
                                } catch (JSONException e) {
                                    uploadFailed();
                                }
                            } catch (ResultProducer.ResultProducerException e) {
                                mLogger.error("Unable to produce ForgeExchangeResult: {}", e);
                                uploadFailed();
                            }
                        } else {
                            mLogger.error("Error uploading file. Code {}", response.code());
                            uploadFailed();
                        }
                    } catch (IOException e) {
                        if (!mCall.isCanceled()) {
                            mLogger.error("Error uploading file: {}", e);
                            uploadFailed();
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
    public long getLastResult() {
        return mLastResult;
    }


    @Override
    public synchronized void abortUpload() {
        if (mState == State.UPLOADING) {
            if (mCall != null) {
                mCall.cancel();
            }
        }
    }


    private synchronized void uploadOk() {
        mState = State.UPLOAD_OK;

        final Act_FileUpload act = (Act_FileUpload) getActivity();
        if (act != null) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    act.onUploadOk();
                }
            });
        }
    }


    private synchronized void uploadFailed() {
        mState = State.UPLOAD_FAIL;

        final Act_FileUpload act = (Act_FileUpload) getActivity();
        if (act != null) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    act.onUploadFailed();
                }
            });
        }
    }
}



