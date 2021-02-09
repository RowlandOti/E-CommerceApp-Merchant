package com.rowland.delivery.merchant.features.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by rowlandoti on 05/02/2021
 *
 */

@Module
@InstallIn(ActivityComponent::class)
object FakeAuthModule {

    @Provides
    @Named("email_login")
    fun providesEmailAuth(authConfig: AuthConfig): Auth {
        return object : Auth() {
            override fun login() {
                authConfig.callback.onLoginSuccess()
            }

            override fun logout(context: Context): Boolean {
                return true
            }

            override fun register() {
                authConfig.callback.onLoginSuccess()
            }

            override fun passwordReset() {}

            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {}
        }
    }

    @Provides
    @Named("google_login")
    fun providesGoogleAuth(authConfig: AuthConfig): Auth {
        return object : Auth() {
            override fun login() {
            }

            override fun logout(context: Context): Boolean {
                return true
            }

            override fun register() {}

            override fun passwordReset() {}

            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {}
        }
    }

    @Provides
    fun providesSocialAuthConfig(@ActivityContext context: Context): AuthConfig {
        return AuthConfig(context as Activity, context as Auth.AuthLoginCallbacks)
    }
}