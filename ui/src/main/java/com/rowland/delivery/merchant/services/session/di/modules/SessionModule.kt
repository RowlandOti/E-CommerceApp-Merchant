package com.rowland.delivery.merchant.services.session.di.modules


import com.rowland.delivery.merchant.services.session.SessionManager
import com.rowland.delivery.merchant.services.sharedpreferences.SharedPreferencesManager
import com.rowland.delivery.merchant.services.sharedpreferences.di.modules.SharedPreferencesModule

import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 4/30/2018.
 */

@Module(includes = [SharedPreferencesModule::class])
class SessionModule {

    @Provides
    fun providesSessionManager(preferencesManager: SharedPreferencesManager): SessionManager {
        return SessionManager(preferencesManager)
    }
}
