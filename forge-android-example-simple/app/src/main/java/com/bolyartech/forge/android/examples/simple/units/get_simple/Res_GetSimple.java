package com.bolyartech.forge.android.examples.simple.units.get_simple;


import com.bolyartech.forge.android.app_unit.ResidentComponent;
import com.bolyartech.forge.base.exchange.ForgeExchangeResult;
import com.bolyartech.forge.base.task.ExchangeManager;


/**
 * Created by ogre on 2015-11-01
 */
public interface Res_GetSimple {
    enum State {
        IDLE,
        WAITING_EXCHANGE,
        EXCHANGE_OK,
        EXCHANGE_FAIL
    }


    State getState();
    void executeSimpleGet();
    SimpleGetResult getLastResult();
    void reset();
}

