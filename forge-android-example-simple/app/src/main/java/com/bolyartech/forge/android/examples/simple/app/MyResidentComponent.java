package com.bolyartech.forge.android.examples.simple.app;

import com.bolyartech.forge.android.examples.simple.misc.ForgeGsonResultProducer;
import com.bolyartech.forge.base.exchange.ForgeExchangeResult;
import com.bolyartech.forge.base.exchange.ResultProducer;
import com.bolyartech.forge.base.exchange.builders.ForgeGetHttpExchangeBuilder;
import com.bolyartech.forge.base.exchange.builders.ForgePostHttpExchangeBuilder;
import com.bolyartech.forge.base.http.HttpFunctionality;
import com.bolyartech.forge.base.task.ExchangeManager;
import com.bolyartech.forge.base.task.ForgeExchangeManager;
import com.squareup.otto.Bus;


/**
 * Created by ogre on 2015-11-02 09:10
 */
abstract public class MyResidentComponent extends BusResidentComponent implements ExchangeManager.Listener<ForgeExchangeResult> {
    private final String mBaseUrl;
    private final ForgeExchangeManager mForgeExchangeManager;

    private HttpFunctionality mHttpFunctionality;

    private ResultProducer<ForgeExchangeResult> mResultProducer = new ForgeGsonResultProducer();


    public MyResidentComponent(String baseUrl,
                               ForgeExchangeManager forgeExchangeManager,
                               Bus bus,
                               HttpFunctionality httpFunctionality) {
        super(bus);
        mBaseUrl = baseUrl;
        mForgeExchangeManager = forgeExchangeManager;
        mHttpFunctionality = httpFunctionality;
    }


    protected ForgeExchangeManager getForgeExchangeManager() {
        return mForgeExchangeManager;
    }


    protected ForgePostHttpExchangeBuilder createForgePostHttpExchangeBuilder(String endpoint) {
        return new ForgePostHttpExchangeBuilder(mHttpFunctionality, mResultProducer, mBaseUrl + endpoint);
    }


    protected ForgeGetHttpExchangeBuilder createForgeGetHttpExchangeBuilder(String endpoint) {
        return new ForgeGetHttpExchangeBuilder(mHttpFunctionality, mResultProducer, mBaseUrl + endpoint);
    }
}
