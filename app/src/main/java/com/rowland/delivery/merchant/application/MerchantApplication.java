package com.rowland.delivery.merchant.application;

import android.app.Application;

import com.rowland.delivery.merchant.application.di.components.ApplicationComponent;
import com.rowland.delivery.merchant.application.di.components.DaggerApplicationComponent;
import com.rowland.delivery.merchant.application.di.modules.ContextModule;


/**
 * Created by Rowland on 4/30/2018.
 */

public class MerchantApplication extends Application {

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
