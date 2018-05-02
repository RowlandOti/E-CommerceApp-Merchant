package com.rowland.delivery.features.auth.di.components;

import com.rowland.delivery.features.auth.ui.AuthActivity;
import com.rowland.delivery.services.auth.di.modules.AuthModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Rowland on 5/1/2018.
 */

@Component(modules = AuthModule.class)
@Singleton
public interface AuthComponent {
    void injectAuthActivity(AuthActivity activity);
}
