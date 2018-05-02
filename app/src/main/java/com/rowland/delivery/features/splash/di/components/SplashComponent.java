package com.rowland.delivery.features.splash.di.components;

import com.rowland.delivery.features.splash.di.modules.SplashModule;
import com.rowland.delivery.features.splash.di.scopes.SplashScope;
import com.rowland.delivery.features.splash.ui.SplashActivity;
import com.rowland.delivery.services.session.SessionManager;
import com.rowland.delivery.services.session.di.modules.SessionModule;


import dagger.Component;

/**
 * Created by Rowland on 4/30/2018.
 */

@Component(modules = {SplashModule.class, SessionModule.class})
@SplashScope
public interface SplashComponent {

    void injectSplashActivity(SplashActivity activity);
}
