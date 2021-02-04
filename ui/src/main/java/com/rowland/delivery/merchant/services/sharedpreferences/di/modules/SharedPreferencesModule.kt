package com.rowland.delivery.merchant.services.sharedpreferences.di.modules

import android.content.Context
import android.preference.PreferenceManager
import com.rowland.delivery.merchant.di.modules.ContextModule
import com.rowland.delivery.merchant.services.sharedpreferences.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by Rowland on 4/30/2018.
 */

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Provides
    fun providesSharedPreferencesManager(@ApplicationContext context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(PreferenceManager.getDefaultSharedPreferences(context))
    }
}
