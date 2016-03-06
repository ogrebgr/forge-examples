//package com.bolyartech.forge.android.examples.simple.units.file_download;
//
//import android.os.Environment;
//
//import com.bolyartech.forge.android.examples.simple.app.BusResidentComponent;
//import com.squareup.otto.Bus;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URI;
//
//
///**
// * Created by ogre on 2015-11-08 18:46
// */
//public class Res_FileDownloadImpl extends BusResidentComponent implements Res_FileDownload {
//    private static final String FILE_TO_DOWNLOAD = "somefile.txt.zip";
//
//    private State mState = State.IDLE;
//
//    private String mDownloadedFilePath;
//
//    private final String mBaseUrl;
//    private final HttpFunctionality mHttpFunc;
//    private HttpGet mGetRequest;
//
//
//    private final ProgressListener mProgressListener = new ProgressListener() {
//        @Override
//        public void onProgress(float progress) {
//
//        }
//    };
//
//
//    public Res_FileDownloadImpl(Bus bus, String baseUrl, HttpFunctionality httpFunc) {
//        super(bus);
//        mBaseUrl = baseUrl;
//        mHttpFunc = httpFunc;
//    }
//
//
//    @Override
//    public State getState() {
//        return mState;
//    }
//
//
//    @Override
//    public synchronized void download() {
//        if (mState == State.IDLE) {
//            mState = State.DOWNLOADING;
//
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    File dlDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//                    File file = new File(dlDir, FILE_TO_DOWNLOAD);
//
//                    // Clean up if not first download
//                    if (file.exists()) {
//                        file.delete();
//                    }
//
//                    URI uri = URI.create(mBaseUrl + FILE_TO_DOWNLOAD);
//                    try {
//                        mGetRequest = new HttpGet(uri);
//                        FileDownloader.download(mHttpFunc, mGetRequest, file, mProgressListener);
//                        mDownloadedFilePath = file.getAbsolutePath();
//                        downloadOk();
//                    } catch (IOException e) {
//                        downloadFailed();
//                    }
//                }
//            });
//            t.start();
//        } else {
//            throw new IllegalStateException("Not in IDLE state");
//        }
//    }
//
//
//    @Override
//    public synchronized void reset() {
//        mState = State.IDLE;
//        mGetRequest = null;
//    }
//
//
//    @Override
//    public String getDownloadedFilePath() {
//        return mDownloadedFilePath;
//    }
//
//
//    @Override
//    public synchronized void abortDownload() {
//        if (mState == State.DOWNLOADING) {
//            mGetRequest.abort();
//        }
//    }
//
//
//    private synchronized void downloadOk() {
//        if (mGetRequest != null && !mGetRequest.isAborted()) {
//            mState = State.DOWNLOAD_OK;
//
//            final Act_FileDownload act = (Act_FileDownload) getActivity();
//            if (act != null) {
//                act.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        act.onDownloadOk();
//                    }
//                });
//            }
//        }
//    }
//
//
//    private synchronized void downloadFailed() {
//        if (mGetRequest != null && !mGetRequest.isAborted()) {
//            mState = State.DOWNLOAD_FAIL;
//
//            final Act_FileDownload act = (Act_FileDownload) getActivity();
//            if (act != null) {
//                act.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        act.onDownloadFailed();
//                    }
//                });
//            }
//        }
//    }
//}
