package com.bolyartech.forge.android.examples.simple.app;

import android.app.Application;

import com.bolyartech.forge.app_unit.UnitManager;
import com.bolyartech.forge.app_unit.UnitManagerImpl;
import com.bolyartech.forge.exchange.ForgeExchangeFunctionality;
import com.bolyartech.forge.http.SimpleHttpClient;
import com.bolyartech.forge.http.functionality.HttpFunctionality;
import com.bolyartech.forge.http.functionality.HttpFunctionalityImpl;
import com.squareup.otto.Bus;

import forge.apache.http.impl.client.HttpClients;


/**
 * Created by ogre on 2015-11-01 16:03
 */
public class MyApplication extends Application {
    private ForgeExchangeFunctionality mForgeExchangeFunctionality;
    private UnitManager mUnitManager;
    private MyForgeExchangeManager mMyForgeExchangeManager;
    private HttpFunctionality mHttpFunctionality;
    private Bus mBus = new Bus();

    @Override
    public void onCreate() {
        super.onCreate();

        mUnitManager = new MyUnitManager();

        mHttpFunctionality = new HttpFunctionalityImpl(new SimpleHttpClient());
        mForgeExchangeFunctionality = new ForgeExchangeFunctionality(mHttpFunctionality);
        mForgeExchangeFunctionality.start();

        mMyForgeExchangeManager = new MyForgeExchangeManager(mUnitManager, mForgeExchangeFunctionality);
        mForgeExchangeFunctionality.addListener(mMyForgeExchangeManager);
    }


    public MyForgeExchangeManager getMyForgeExchangeManager() {
        return mMyForgeExchangeManager;
    }


    public void shutdown() {
        mForgeExchangeFunctionality.shutdown();
        mForgeExchangeFunctionality = null;
        mUnitManager = null;
        mMyForgeExchangeManager = null;
    }


    public UnitManager getUnitManager() {
        return mUnitManager;
    }


    public Bus getBus() {
        return mBus;
    }


    public HttpFunctionality getHttpFunctionality() {
        return mHttpFunctionality;
    }
}
