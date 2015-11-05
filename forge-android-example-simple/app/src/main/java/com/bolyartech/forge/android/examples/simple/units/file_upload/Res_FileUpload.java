package com.bolyartech.forge.android.examples.simple.units.file_upload;

import com.bolyartech.forge.exchange.ForgeExchangeFunctionality;
import com.bolyartech.forge.exchange.ForgeExchangeResult;

import java.io.File;


/**
 * Created by ogre on 2015-11-05 08:39
 */
public interface Res_FileUpload extends ForgeExchangeFunctionality.Listener<ForgeExchangeResult>{
    enum State {
        IDLE,
        WAITING_EXCHANGE,
        EXCHANGE_OK,
        EXCHANGE_FAIL
    }


    State getState();
    void upload(File file);
    void reset();
    long getLastResult();
}
