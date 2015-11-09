package com.bolyartech.forge.android.examples.simple.units.file_upload;

import com.bolyartech.forge.android.examples.simple.app.BusResidentComponent;
import com.bolyartech.forge.android.examples.simple.misc.GsonResultProducer;
import com.bolyartech.forge.exchange.ForgeExchangeResult;
import com.bolyartech.forge.exchange.ResultProducer;
import com.bolyartech.forge.http.functionality.HttpFunctionality;
import com.bolyartech.forge.http.request.PostRequestBuilder;
import com.bolyartech.forge.http.request.ProgressListener;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import forge.apache.http.client.methods.HttpPost;


/**
 * Created by ogre on 2015-11-05 10:18
 */
public class Res_FileUploadImpl extends BusResidentComponent implements Res_FileUpload {
    private State mState = State.IDLE;

    private long mLastResult;

    private final String mBaseUrl;
    private final HttpFunctionality mHttpFunc;

    private HttpPost mPostRequest;


    private ProgressListener mProgressListener = new ProgressListener() {
        @Override
        public void onProgress(final float progress) {
            final Act_FileUpload act = (Act_FileUpload) getActivity();
            if (act != null) {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        act.onProgress(progress);
                    }
                });
            }
        }
    };


    public Res_FileUploadImpl(Bus bus, String baseUrl, HttpFunctionality httpFunc) {
        super(bus);
        mBaseUrl = baseUrl;
        mHttpFunc = httpFunc;
    }



    @Override
    public State getState() {
        return mState;
    }


    @Override
    public synchronized void upload(final File file) {
        if (mState == State.IDLE) {
            mState = State.UPLOADING;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    PostRequestBuilder builder = new PostRequestBuilder(mBaseUrl + "upload.php");
                    builder.fileToUpload("file_upload", file);
                    builder.progressListener(mProgressListener);
                    try {
                        mPostRequest = builder.build();

                        String rez = mHttpFunc.execute(mPostRequest);
                        GsonResultProducer rp = new GsonResultProducer();
                        try {
                            ForgeExchangeResult frez = rp.produce(rez, ForgeExchangeResult.class);
                            try {
                                JSONObject jobj = new JSONObject(frez.getPayload());
                                mLastResult = jobj.getLong("file_size");
                                uploadOk();
                            } catch (JSONException e) {
                                uploadFailed();
                            }
                        } catch (ResultProducer.ResultProducerException e) {
                            uploadFailed();
                        }

                    } catch (IOException e) {
                        uploadFailed();
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
        mPostRequest = null;
    }


    @Override
    public long getLastResult() {
        return mLastResult;
    }


    @Override
    public synchronized void abortUpload() {
        if (mState == State.UPLOADING) {
            mPostRequest.abort();
        }
    }


    private synchronized void uploadOk() {
        if (mPostRequest != null && !mPostRequest.isAborted()) {
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
    }


    private synchronized void uploadFailed() {
        if (mPostRequest != null && !mPostRequest.isAborted()) {
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
}
