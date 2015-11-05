package com.bolyartech.forge.android.examples.simple.units.file_upload;

import com.bolyartech.forge.android.examples.simple.app.MyForgeExchangeManager;
import com.bolyartech.forge.android.examples.simple.app.MyResidentComponent;
import com.bolyartech.forge.android.examples.simple.misc.ResponseCodes;
import com.bolyartech.forge.exchange.Exchange;
import com.bolyartech.forge.exchange.ExchangeOutcome;
import com.bolyartech.forge.exchange.ForgeExchangeBuilder;
import com.bolyartech.forge.exchange.ForgeExchangeResult;
import com.bolyartech.forge.exchange.RestExchangeBuilder;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


/**
 * Created by ogre on 2015-11-05 10:18
 */
public class Res_FileUploadImpl extends MyResidentComponent implements Res_FileUpload {
    private State mState = State.IDLE;

    private long mExchangeId;
    private long mLastResult;


    public Res_FileUploadImpl(String baseUrl, MyForgeExchangeManager myForgeExchangeManager, Bus bus) {
        super(baseUrl, myForgeExchangeManager, bus);
    }



    @Override
    public State getState() {
        return mState;
    }


    @Override
    public void upload(File file) {
        ForgeExchangeBuilder builder = createForgeExchangeBuilder("upload.php");
        builder.requestType(RestExchangeBuilder.RequestType.POST);
        builder.addFileToUpload("file_upload", file);
        Exchange<ForgeExchangeResult> x = builder.build();
        getMyForgeExchangeManager().executeExchange(x, mExchangeId);
    }


    @Override
    public void reset() {
        mState = State.IDLE;
    }


    @Override
    public long getLastResult() {
        return mLastResult;
    }


    @Override
    public void onExchangeCompleted(ExchangeOutcome<ForgeExchangeResult> outcome, long exchangeId) {
        if (exchangeId == mExchangeId) {
            handleExchangeOutcome(outcome, exchangeId);
        }
    }


    private void handleExchangeOutcome(ExchangeOutcome<ForgeExchangeResult> outcome, long exchangeId) {
        if (!outcome.isError()) {
            ForgeExchangeResult rez = outcome.getResult();
            int code = rez.getCode();
            if (code > 0) {
                if (code == ResponseCodes.Oks.UPLOAD_OK.getCode()) {
                    try {
                        JSONObject json = new JSONObject(rez.getPayload());
                        mLastResult = json.getLong("file_size");
                        exchangeOk();
                    } catch (JSONException e) {
                        exchangeFailed();
                    }
                } else {
                    exchangeFailed();
                }
            } else {
                exchangeFailed();
            }
        } else {
            exchangeFailed();
        }
    }


    private void exchangeOk() {
        mState = State.EXCHANGE_OK;


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


    private void exchangeFailed() {
        mState = State.EXCHANGE_FAIL;


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
