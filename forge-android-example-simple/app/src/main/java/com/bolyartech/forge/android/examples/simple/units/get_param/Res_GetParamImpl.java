package com.bolyartech.forge.android.examples.simple.units.get_param;

import android.support.annotation.UiThread;

import com.bolyartech.forge.android.examples.simple.app.MyForgeExchangeManager;
import com.bolyartech.forge.android.examples.simple.app.MyResidentComponent;
import com.bolyartech.forge.android.examples.simple.misc.ResponseCodes;
import com.bolyartech.forge.android.examples.simple.units.get_simple.Act_GetSimple;
import com.bolyartech.forge.android.examples.simple.units.get_simple.Res_GetSimple;
import com.bolyartech.forge.android.examples.simple.units.get_simple.SimpleGetResult;
import com.bolyartech.forge.exchange.Exchange;
import com.bolyartech.forge.exchange.ExchangeOutcome;
import com.bolyartech.forge.exchange.ForgeExchangeBuilder;
import com.bolyartech.forge.exchange.ForgeExchangeResult;
import com.bolyartech.forge.exchange.RestExchangeBuilder;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ogre on 2015-11-01
 */
public class Res_GetParamImpl extends MyResidentComponent implements Res_GetParam {
    private State mState = State.IDLE;
    private String mLastResult;

    private long mExchangeId;


    public Res_GetParamImpl(String baseUrl, MyForgeExchangeManager myForgeExchangeManager, Bus bus) {
        super(baseUrl, myForgeExchangeManager, bus);
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
        mExchangeId = getMyForgeExchangeManager().generateXId();

        ForgeExchangeBuilder builder = createForgeExchangeBuilder("get_param.php");
        builder.requestType(RestExchangeBuilder.RequestType.GET);
        builder.addGetParameter("param", value);

        Exchange<ForgeExchangeResult> x = builder.build();
        getMyForgeExchangeManager().executeExchange(x, mExchangeId);
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
                if (code == ResponseCodes.Oks.GET_PARAM_OK.getCode()) {
                    try {
                        JSONObject json = new JSONObject(rez.getPayload());
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
