package com.rowland.delivery.merchant.application;

import android.support.multidex.MultiDexApplication;

import com.rowland.delivery.merchant.application.di.components.ApplicationComponent;
import com.rowland.delivery.merchant.application.di.components.DaggerApplicationComponent;
import com.rowland.delivery.merchant.application.di.modules.ContextModule;


/**
 * Created by Rowland on 4/30/2018.
 */

public class MerchantApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
