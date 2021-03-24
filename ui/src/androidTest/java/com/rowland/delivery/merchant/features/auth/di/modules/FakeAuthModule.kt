/*
 * Copyright 2021 Otieno Rowland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.merchant.features.auth.di.modules

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.rowland.delivery.merchant.features.auth.Auth
import com.rowland.delivery.merchant.features.auth.AuthConfig
import com.rowland.delivery.merchant.features.auth.AuthException
import com.rowland.delivery.merchant.features.auth.EmailAuth
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
        return FakeEmailAuth(authConfig)
    }

    @Provides
    @Named("google_login")
    fun providesGoogleAuth(authConfig: AuthConfig): Auth {
        return FakeGoogleAuth(authConfig)
    }

    @Provides
    fun providesSocialAuthConfig(@ActivityContext context: Context): AuthConfig {
        return AuthConfig(context as Activity, context as
                Auth.AuthLoginCallbacks)
    }

    class FakeEmailAuth(private val authConfig: AuthConfig) : Auth() {

        override fun login() {
            val credentials = authConfig.callback.doEmailLogin()
            val email = credentials[EmailAuth.CRED_EMAIL_KEY]
            val password = credentials[EmailAuth.CRED_PASSWORD_KEY]

            if ((email.isNullOrEmpty() || password.isNullOrEmpty()) || (
                        email.contains(
                            WRONG_USER_EMAIL,
                            true
                        ) || password.contains(
                            WRONG_USER_PASS, true
                        )
                        )
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

        override fun logout(context: Context): Boolean = true

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

    class FakeGoogleAuth(private val authConfig: AuthConfig) : Auth() {

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
