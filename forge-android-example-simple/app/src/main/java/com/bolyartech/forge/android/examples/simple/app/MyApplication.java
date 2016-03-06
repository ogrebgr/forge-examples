package com.bolyartech.forge.android.examples.simple.app;

import com.bolyartech.forge.android.app_unit.UnitApplication;
import com.bolyartech.forge.base.http.HttpFunctionality;
import com.bolyartech.forge.base.http.HttpFunctionalityImpl;
import com.bolyartech.forge.base.task.ForgeExchangeManager;
import com.bolyartech.forge.base.task.ForgeTaskExecutor;
import com.squareup.otto.Bus;

import okhttp3.OkHttpClient;


/**
 * Created by ogre on 2015-11-01 16:03
 */
public class MyApplication extends UnitApplication {
    private ForgeExchangeManager mForgeExchangeManager = new ForgeExchangeManager(new ForgeTaskExecutor());

    private MyUnitManager mUnitManager;

    private HttpFunctionality mHttpFunctionality;
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private Bus mBus = new Bus();


    @Override
    public void onCreate() {
        mUnitManager = new MyUnitManager();
        super.setUnitManager(mUnitManager);
        super.onCreate();


        mForgeExchangeManager.start();



        mHttpFunctionality = new HttpFunctionalityImpl(mOkHttpClient);


        mForgeExchangeManager = new ForgeExchangeManager(new ForgeTaskExecutor());
        mForgeExchangeManager.addListener(mUnitManager);
    }


    public ForgeExchangeManager getForgeExchangeManager() {
        return mForgeExchangeManager;
    }


    public void shutdown() {
        mForgeExchangeManager.shutdown();
        mForgeExchangeManager = null;
        mUnitManager = null;
    }


    public MyUnitManager getMyUnitManager() {
        return mUnitManager;
    }


    public Bus getBus() {
        return mBus;
    }


    public HttpFunctionality getHttpFunctionality() {
        return mHttpFunctionality;
    }


    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
