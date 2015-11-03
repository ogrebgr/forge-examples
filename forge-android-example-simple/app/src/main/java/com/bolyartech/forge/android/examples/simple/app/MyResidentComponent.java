package com.bolyartech.forge.android.examples.simple.app;

import com.bolyartech.forge.android.examples.simple.misc.GsonResultProducer;
import com.bolyartech.forge.app_unit.AbstractResidentComponent;
import com.bolyartech.forge.exchange.ForgeExchangeBuilder;
import com.bolyartech.forge.exchange.ForgeExchangeResult;
import com.bolyartech.forge.misc.AndroidEventPoster;
import com.squareup.otto.Bus;


/**
 * Created by ogre on 2015-11-02 09:10
 */
abstract public class MyResidentComponent extends AbstractResidentComponent {
    private final String mBaseUrl;
    private final MyForgeExchangeManager mMyForgeExchangeManager;
    private final Bus mBus;
    private final AndroidEventPoster mAndroidEventPoster = new AndroidEventPoster();

    public MyResidentComponent(String baseUrl, MyForgeExchangeManager myForgeExchangeManager, Bus bus) {
        mBaseUrl = baseUrl;
        mMyForgeExchangeManager = myForgeExchangeManager;
        mBus = bus;
    }


    protected ForgeExchangeBuilder createForgeExchangeBuilder(String endpoint){
        ForgeExchangeBuilder b = new ForgeExchangeBuilder();
        b.baseUrl(mBaseUrl);
        b.endpoint(endpoint);
        b.resultProducer(new GsonResultProducer());
        b.resultClass(ForgeExchangeResult.class);

        return b;
    }


    public MyForgeExchangeManager getMyForgeExchangeManager() {
        return mMyForgeExchangeManager;
    }



    protected void postEvent(MyEvent ev) {
        mBus.post(ev);
    }
}
