package com.bolyartech.forge.android.examples.simple.units.get_param;


/**
 * Created by ogre on 2015-11-01
 */
public interface Res_GetParam {
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

