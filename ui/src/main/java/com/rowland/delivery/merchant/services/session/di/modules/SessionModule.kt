package com.rowland.delivery.merchant.services.session.di.modules


import com.rowland.delivery.merchant.services.session.SessionManager
import com.rowland.delivery.merchant.services.sharedpreferences.SharedPreferencesManager
import com.rowland.delivery.merchant.services.sharedpreferences.di.modules.SharedPreferencesModule

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

/**
 * Created by Rowland on 4/30/2018.
 */

@Module(includes = [SharedPreferencesModule::class])
@InstallIn(SingletonComponent::class)
class SessionModule {

    @Provides
    fun providesSessionManager(preferencesManager: SharedPreferencesManager): SessionManager {
        return SessionManager(preferencesManager)
    }
}
