package com.bolyartech.forge.android.examples.simple.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bolyartech.forge.android.app_unit.ResidentComponent;
import com.bolyartech.forge.android.app_unit.UnitManager;

import javax.inject.Inject;


/**
 * Created by ogre on 2015-11-01 16:52
 */
abstract public class MyActivity extends AppCompatActivity {
    private UnitManager mUnitManager;
    private ResidentComponent mResidentComponent;


    public void setResidentComponent(ResidentComponent res) {
        mResidentComponent = res;
    }


    public ResidentComponent getResidentComponent() {
        return mResidentComponent;
    }


    public MyApplication getMyApp() {
        return (MyApplication) super.getApplication();
    }


    public void setUnitManager(ResidentComponent res) {
        mResidentComponent = res;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUnitManager(getMyApp().getUnitManager());
    }
}
