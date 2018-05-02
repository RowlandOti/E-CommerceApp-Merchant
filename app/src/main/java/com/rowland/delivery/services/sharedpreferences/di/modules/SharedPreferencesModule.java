package com.rowland.delivery.services.sharedpreferences.di.modules;

import android.content.Context;
import android.preference.PreferenceManager;

import com.rowland.delivery.merchant.application.di.modules.ContextModule;
import com.rowland.delivery.services.sharedpreferences.SharedPreferencesManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 4/30/2018.
 */

@Module(includes = ContextModule.class)
public class SharedPreferencesModule {

    @Provides
    public SharedPreferencesManager providesSharedPreferencesManager(Context context) {
        return new SharedPreferencesManager(PreferenceManager.getDefaultSharedPreferences(context));
    }
}
