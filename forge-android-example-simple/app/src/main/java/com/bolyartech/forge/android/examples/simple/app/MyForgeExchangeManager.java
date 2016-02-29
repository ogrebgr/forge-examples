package com.bolyartech.forge.android.examples.simple.app;

import com.bolyartech.forge.app_unit.ResidentComponent;
import com.bolyartech.forge.app_unit.UnitManager;
import com.bolyartech.forge.exchange.Exchange;
import com.bolyartech.forge.exchange.ExchangeFunctionality;
import com.bolyartech.forge.exchange.ExchangeOutcome;
import com.bolyartech.forge.exchange.ForgeExchangeFunctionality;
import com.bolyartech.forge.exchange.ForgeExchangeManager;
import com.bolyartech.forge.exchange.ForgeExchangeResult;
import com.bolyartech.forge.misc.ForgeExchangeManagerImpl;


/**
 * Created by ogre on 2015-11-01 16:07
 */
public class MyForgeExchangeManager extends ForgeExchangeManagerImpl {
    public MyForgeExchangeManager(UnitManager unitManager, ForgeExchangeFunctionality forgeExchangeFunctionality) {
        super(unitManager, forgeExchangeFunctionality);
    }
}
