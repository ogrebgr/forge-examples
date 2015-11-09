package com.bolyartech.forge.android.examples.simple.units.get_simple;

import android.support.annotation.UiThread;

import com.bolyartech.forge.android.examples.simple.app.MyForgeExchangeManager;
import com.bolyartech.forge.android.examples.simple.app.MyResidentComponent;
import com.bolyartech.forge.android.examples.simple.misc.ResponseCodes;
import com.bolyartech.forge.exchange.Exchange;
import com.bolyartech.forge.exchange.ExchangeOutcome;
import com.bolyartech.forge.exchange.ForgeExchangeResult;
import com.bolyartech.forge.exchange.RestExchangeBuilder;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ogre on 2015-11-01
 */
public class Res_GetSimpleImpl extends MyResidentComponent implements Res_GetSimple {
    private State mState = State.IDLE;
    private SimpleGetResult mLastSimpleGetResult;

    private long mExchangeId;


    public Res_GetSimpleImpl(String baseUrl, MyForgeExchangeManager myForgeExchangeManager, Bus bus) {
        super(baseUrl, myForgeExchangeManager, bus);
    }


    @UiThread
    @Override
    public State getState() {
        return mState;
    }


    @UiThread
    @Override
    public void executeSimpleGet() {
        if (mState != State.IDLE) {
            throw new IllegalStateException("Not in IDLE state");
        }

        mState = State.WAITING_EXCHANGE;
        mExchangeId = getMyForgeExchangeManager().generateXId();
        Exchange<ForgeExchangeResult> x = createForgeExchangeBuilder("get_simple.php")
                .requestType(RestExchangeBuilder.RequestType.GET)
                .build();
        getMyForgeExchangeManager().executeExchange(x, mExchangeId);
    }


    @UiThread
    @Override
    public SimpleGetResult getLastResult() {
        return mLastSimpleGetResult;
    }


    @UiThread
    @Override
    public void reset() {
        if (mState == State.EXCHANGE_OK || mState == State.EXCHANGE_FAIL || mState == State.IDLE) {
            mState = State.IDLE;
            mLastSimpleGetResult = null;
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
                if (code == ResponseCodes.Oks.SIMPLE_GET_OK.getCode()) {
                    try {
                        JSONObject json = new JSONObject(rez.getPayload());
                        mLastSimpleGetResult = new SimpleGetResult(json.getInt("var1"), json.getString("var2"));
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


        final Act_GetSimple act = (Act_GetSimple) getActivity();
        if (act != null) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    act.onSimpleGetOk();
                }
            });
        }
    }


    private void exchangeFailed() {
        mState = State.EXCHANGE_FAIL;


        final Act_GetSimple act = (Act_GetSimple) getActivity();
        if (act != null) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    act.onSimpleGetFailed();
                }
            });
        }
    }
}
