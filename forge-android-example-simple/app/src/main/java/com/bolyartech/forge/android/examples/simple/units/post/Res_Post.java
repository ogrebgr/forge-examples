package com.bolyartech.forge.android.examples.simple.units.post;


/**
 * Created by ogre on 2015-11-01
 */
public interface Res_Post {
    enum State {
        IDLE,
        WAITING_EXCHANGE,
        EXCHANGE_OK,
        EXCHANGE_FAIL
    }


    State getState();
    void executePostParam(String value);
    String getLastResult();
    void reset();
}

