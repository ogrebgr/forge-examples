package com.bolyartech.forge.android.examples.simple.units.get_param;

import com.bolyartech.forge.android.examples.simple.units.get_simple.SimpleGetResult;
import com.bolyartech.forge.app_unit.ResidentComponent;
import com.bolyartech.forge.exchange.ForgeExchangeFunctionality;
import com.bolyartech.forge.exchange.ForgeExchangeResult;


/**
 * Created by ogre on 2015-11-01
 */
public interface Res_GetParam extends ResidentComponent, ForgeExchangeFunctionality.Listener<ForgeExchangeResult> {
    enum State {
        IDLE,
        WAITING_EXCHANGE,
        EXCHANGE_OK,
        EXCHANGE_FAIL
    }


    State getState();
    void executeGetParam(String value);
    String getLastResult();
    void reset();
}

