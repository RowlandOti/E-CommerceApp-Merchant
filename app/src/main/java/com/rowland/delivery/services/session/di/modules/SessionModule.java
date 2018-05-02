package com.rowland.delivery.services.session.di.modules;

import com.rowland.delivery.services.session.SessionManager;
import com.rowland.delivery.services.sharedpreferences.SharedPreferencesManager;
import com.rowland.delivery.services.sharedpreferences.di.modules.SharedPreferencesModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 4/30/2018.
 */

@Module(includes = SharedPreferencesModule.class)
public class SessionModule {

    @Provides
    public SessionManager providesSessionManager(SharedPreferencesManager preferencesManager) {
        return new SessionManager(preferencesManager);
    }
}
