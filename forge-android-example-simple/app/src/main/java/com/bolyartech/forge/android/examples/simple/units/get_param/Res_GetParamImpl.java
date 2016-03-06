package com.bolyartech.forge.android.examples.simple.units.get_param;

import android.support.annotation.UiThread;

import com.bolyartech.forge.android.examples.simple.app.MyResidentComponent;
import com.bolyartech.forge.android.examples.simple.misc.ResponseCodes;
import com.bolyartech.forge.base.exchange.ForgeExchangeResult;
import com.bolyartech.forge.base.exchange.builders.ForgeGetHttpExchangeBuilder;
import com.bolyartech.forge.base.http.HttpFunctionality;
import com.bolyartech.forge.base.task.ExchangeManager;
import com.bolyartech.forge.base.task.ForgeExchangeManager;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ogre on 2015-11-01
 */
public class Res_GetParamImpl extends MyResidentComponent implements Res_GetParam, ExchangeManager.Listener<ForgeExchangeResult> {
    private State mState = State.IDLE;
    private String mLastResult;

    private long mExchangeId;


    public Res_GetParamImpl(String baseUrl, ForgeExchangeManager forgeExchangeManager, Bus bus, HttpFunctionality httpFunctionality) {
        super(baseUrl, forgeExchangeManager, bus, httpFunctionality);
    }


    @UiThread
    @Override
    public State getState() {
        return mState;
    }


    @UiThread
    @Override
    public void executeGetParam(String value) {
        if (mState != State.IDLE) {
            throw new IllegalStateException("Not in IDLE state");
        }

        mState = State.WAITING_EXCHANGE;

        ForgeGetHttpExchangeBuilder b = createForgeGetHttpExchangeBuilder("get_param.php");
        b.addGetParameter("param", value);

        ForgeExchangeManager em = getForgeExchangeManager();
        mExchangeId = em.generateTaskId();
        em.executeExchange(b.build(), mExchangeId);
    }



    @UiThread
    @Override
    public String getLastResult() {
        return mLastResult;
    }


    @UiThread
    @Override
    public void reset() {
        if (mState == State.EXCHANGE_OK || mState == State.EXCHANGE_FAIL || mState == State.IDLE) {
            mState = State.IDLE;
            mLastResult = null;
        } else {
            throw new IllegalStateException("Cannot reset in UPLOADING state");
        }
    }



    @Override
    public void onExchangeOutcome(long exchangeId, boolean isSuccess, ForgeExchangeResult result) {
        if (exchangeId == mExchangeId) {
            if (isSuccess) {
                int code = result.getCode();
                if (code > 0) {
                    if (code == ResponseCodes.Oks.GET_PARAM_OK.getCode()) {
                        try {
                            JSONObject json = new JSONObject(result.getPayload());
                            mLastResult = json.getString("param");
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
    }


    private void exchangeOk() {
        mState = State.EXCHANGE_OK;


        final Act_GetParam act = (Act_GetParam) getActivity();
        if (act != null) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    act.onGetParamOk();
                }
            });
        }
    }


    private void exchangeFailed() {
        mState = State.EXCHANGE_FAIL;


        final Act_GetParam act = (Act_GetParam) getActivity();
        if (act != null) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    act.onGetParamFailed();
                }
            });
        }
    }
}
