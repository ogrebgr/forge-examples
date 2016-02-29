package com.bolyartech.forge.android.examples.simple.app;

import android.app.Application;

import com.bolyartech.forge.android.app_unit.UnitManager;
import com.bolyartech.forge.base.http.HttpFunctionality;
import com.bolyartech.forge.base.http.HttpFunctionalityImpl;
import com.bolyartech.forge.base.task.ForgeExchangeManager;
import com.bolyartech.forge.base.task.ForgeTaskExecutor;
import com.squareup.otto.Bus;

import okhttp3.OkHttpClient;


/**
 * Created by ogre on 2015-11-01 16:03
 */
public class MyApplication extends Application {
    private ForgeExchangeManager mForgeExchangeManager = new ForgeExchangeManager(new ForgeTaskExecutor());

    private UnitManager mUnitManager;

    private MyForgeExchangeManager mMyForgeExchangeManager;
    private HttpFunctionality mHttpFunctionality;
    private Bus mBus = new Bus();


    @Override
    public void onCreate() {
        super.onCreate();

        mForgeExchangeManager.start();

        mUnitManager = new MyUnitManager();

        mHttpFunctionality = new HttpFunctionalityImpl(new OkHttpClient());


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
