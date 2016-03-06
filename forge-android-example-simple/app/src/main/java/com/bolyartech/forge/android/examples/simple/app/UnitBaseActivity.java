package com.bolyartech.forge.android.examples.simple.app;

import android.support.v7.app.AppCompatActivity;

import com.bolyartech.forge.android.app_unit.ActivityComponent;
import com.bolyartech.forge.android.app_unit.ResidentComponent;


abstract public class UnitBaseActivity extends AppCompatActivity implements ActivityComponent {
    private ResidentComponent mResidentComponent;


    @Override
    public void setResidentComponent(ResidentComponent res) {
        mResidentComponent = res;
    }


    @Override
    public ResidentComponent getResidentComponent() {
        return mResidentComponent;
    }
}
