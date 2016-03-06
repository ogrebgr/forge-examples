package com.bolyartech.forge.android.examples.simple.app;


import com.bolyartech.forge.android.app_unit.ResidentComponent;
import com.bolyartech.forge.android.app_unit.UnitManagerImpl;
import com.bolyartech.forge.base.exchange.ForgeExchangeResult;
import com.bolyartech.forge.base.task.ExchangeManager;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by ogre on 2015-11-03 09:44
 */
@Singleton
public class MyUnitManager extends UnitManagerImpl implements ExchangeManager.Listener<ForgeExchangeResult> {
    @Inject
    public MyUnitManager() {
    }


    @Override
    public void onExchangeOutcome(long l, boolean b, ForgeExchangeResult forgeExchangeResult) {
        if (getActiveResidentComponent() instanceof MyResidentComponent) {
            MyResidentComponent sc = (MyResidentComponent) getActiveResidentComponent();
            sc.onExchangeOutcome(l, b, forgeExchangeResult);
        }
    }
}
