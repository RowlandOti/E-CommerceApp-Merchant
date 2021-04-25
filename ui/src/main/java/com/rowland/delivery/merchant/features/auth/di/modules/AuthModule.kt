/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
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
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.di.modules.FirebaseModule
import com.rowland.delivery.merchant.features.auth.Auth
import com.rowland.delivery.merchant.features.auth.AuthConfig
import com.rowland.delivery.merchant.features.auth.EmailAuth
import com.rowland.delivery.merchant.features.auth.GoogleAuth
import com.rowland.delivery.merchant.services.session.SessionManager
import com.rowland.delivery.merchant.services.session.di.modules.SessionModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Named

/**
 * Created by Rowland on 5/1/2018.
 */

@Module(includes = [SessionModule::class, FirebaseModule::class])
@InstallIn(ActivityComponent::class)
class AuthModule {

    @Provides
    @Named("email_login")
    fun providesEmailAuth(
        authConfig: AuthConfig,
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        sessionManager: SessionManager
    ): Auth {
        return EmailAuth(authConfig, firebaseAuth, firebaseFirestore, sessionManager)
    }

    @Provides
    @Named("google_login")
    fun providesGoogleAuth(
        authConfig: AuthConfig,
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        sessionManager: SessionManager
    ): Auth {
        return GoogleAuth(
            authConfig,
            firebaseAuth,
            firebaseFirestore,
            sessionManager
        )
    }

    @Provides
    fun providesSocialAuthConfig(
        @ActivityContext context: Context,
        googleSignInClient: GoogleSignInClient
    ): AuthConfig {
        return AuthConfig(
            context as Activity,
            context as Auth.AuthLoginCallbacks
        )
            .setGoogleSignInClient(googleSignInClient)
    }

    @Provides
    fun providesGoogleSignInClient(
        @ActivityContext context: Context,
        gso: GoogleSignInOptions
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(context as FragmentActivity, gso)
    }

    @Provides
    fun providesGoogleSignOptions(@ActivityContext context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
    }
}
