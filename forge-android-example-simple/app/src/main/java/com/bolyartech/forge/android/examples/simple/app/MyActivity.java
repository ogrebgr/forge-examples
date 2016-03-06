package com.bolyartech.forge.android.examples.simple.app;

import com.bolyartech.forge.android.app_unit.ActivityComponent;
import com.bolyartech.forge.android.app_unit.ResidentComponent;


/**
 * Created by ogre on 2015-11-01 16:52
 */
abstract public class MyActivity extends UnitBaseActivity implements ActivityComponent {
    public MyApplication getMyApp() {
        return (MyApplication) super.getApplication();
    }
}