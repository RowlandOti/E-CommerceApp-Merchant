package com.rowland.delivery.merchant.application.di.modules;

import android.app.Application;
import android.content.Context;

import com.rowland.delivery.merchant.application.di.scopes.ApplicationContextQualifier;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 4/30/2018.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context providesContext() {
        return this.context;
    }
}
