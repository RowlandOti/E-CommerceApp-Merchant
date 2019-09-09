package com.rowland.delivery.merchant.services.sharedpreferences.di.modules

import android.content.Context
import android.preference.PreferenceManager

import com.rowland.delivery.merchant.di.modules.ContextModule
import com.rowland.delivery.merchant.services.sharedpreferences.SharedPreferencesManager

import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 4/30/2018.
 */

@Module(includes = [ContextModule::class])
class SharedPreferencesModule {

    @Provides
    fun providesSharedPreferencesManager(context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(PreferenceManager.getDefaultSharedPreferences(context))
    }
}
