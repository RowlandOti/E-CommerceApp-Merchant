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

/**
 * Created by rowlandoti on 05/02/2021
 *
 */

@Module
@InstallIn(ActivityComponent::class)
object FakeAuthModule {

    const val USER_EMAIL = "right@delivery.com"
    const val USER_PASS = "rightqsWercjdGg"

    const val WRONG_USER_EMAIL = "wrong@delivery.com"
    const val WRONG_USER_PASS = "wrongqsWercjdGg"

    @Provides
    @Named("email_login")
    fun providesEmailAuth(authConfig: AuthConfig): Auth {
        return object : Auth() {
            override fun login() {
                val credentials = authConfig.callback.doEmailLogin()
                val email = credentials[EmailAuth.CRED_EMAIL_KEY]
                val password = credentials[EmailAuth.CRED_PASSWORD_KEY]

                if ((email.isNullOrEmpty() || password.isNullOrEmpty()) || (email.contains(
                        WRONG_USER_EMAIL,
                        true
                    ) || password.contains(
                        WRONG_USER_PASS, true
                    ))
                ) {
                    authConfig.callback.onLoginFailure(
                        AuthException(
                            "Error logging in with null credential",
                            Throwable()
                        )
                    )
                    return
                }

                authConfig.callback.onLoginSuccess()
            }

            override fun logout(context: Context): Boolean {
                return true
            }

            override fun register() {
                val credentials = authConfig.callback.doEmailRegister()
                val email = credentials[EmailAuth.CRED_EMAIL_KEY]
                val password = credentials[EmailAuth.CRED_PASSWORD_KEY]

                if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                    authConfig.callback.onLoginFailure(
                        AuthException(
                            "Error logging in with null credential",
                            Throwable()
                        )
                    )
                    return
                }
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
                authConfig.callback.onLoginSuccess()
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