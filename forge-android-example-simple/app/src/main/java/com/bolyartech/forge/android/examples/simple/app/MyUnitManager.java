package com.bolyartech.forge.android.examples.simple.app;

import com.bolyartech.forge.app_unit.UnitManagerImpl;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by ogre on 2015-11-03 09:44
 */
@Singleton
public class MyUnitManager extends UnitManagerImpl {
    @Inject
    public MyUnitManager() {
    }
}
