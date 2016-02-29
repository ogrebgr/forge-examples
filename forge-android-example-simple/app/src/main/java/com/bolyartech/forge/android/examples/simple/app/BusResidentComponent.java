package com.bolyartech.forge.android.examples.simple.app;

import com.bolyartech.forge.android.app_unit.AbstractResidentComponent;
import com.squareup.otto.Bus;


/**
 * Created by ogre on 2015-11-08 15:17
 */
public class BusResidentComponent extends AbstractResidentComponent {
    private final Bus mBus;


    public BusResidentComponent(Bus bus) {
        mBus = bus;
    }


    protected void postEvent(MyEvent ev) {
        mBus.post(ev);
    }
}
