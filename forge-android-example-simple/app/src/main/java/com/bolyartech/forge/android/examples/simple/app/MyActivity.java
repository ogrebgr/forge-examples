package com.bolyartech.forge.android.examples.simple.app;

import android.os.Bundle;

import com.bolyartech.forge.app_unit.ActivityComponentImpl;


/**
 * Created by ogre on 2015-11-01 16:52
 */
abstract public class MyActivity extends ActivityComponentImpl {
    public MyApplication getMyApp() {
        return (MyApplication) super.getApplication();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUnitManager(getMyApp().getUnitManager());
    }
}
