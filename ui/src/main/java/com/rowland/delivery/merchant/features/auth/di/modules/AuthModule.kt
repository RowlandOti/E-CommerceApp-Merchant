package com.rowland.delivery.merchant.features.auth.di.modules


import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.di.modules.ContextModule
import com.rowland.delivery.merchant.features.auth.Auth
import com.rowland.delivery.merchant.features.auth.AuthConfig
import com.rowland.delivery.merchant.features.auth.EmailAuth
import com.rowland.delivery.merchant.features.auth.GoogleAuth
import com.rowland.delivery.merchant.services.firebase.modules.FirebaseModule
import com.rowland.delivery.merchant.services.session.SessionManager
import com.rowland.delivery.merchant.services.session.di.modules.SessionModule
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Rowland on 5/1/2018.
 */

@Module(includes = arrayOf(ContextModule::class, SessionModule::class, FirebaseModule::class))
class AuthModule {

    @Provides
    @Singleton
    @Named("email_login")
    fun providesEmailAuth(authConfig: AuthConfig, firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore, sessionManager: SessionManager): Auth {
        return EmailAuth(authConfig, firebaseAuth, firebaseFirestore, sessionManager)
    }

    @Provides
    @Singleton
    @Named("google_login")
    fun providesGoogleAuth(authConfig: AuthConfig, firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore, sessionManager: SessionManager): Auth {
        return GoogleAuth(authConfig, firebaseAuth, firebaseFirestore, sessionManager)
    }

    @Provides
    @Singleton
    fun providesSocialAuthConfig(context: Context, googleApiClient: GoogleApiClient): AuthConfig {
        return AuthConfig(context as Activity, context as Auth.AuthLoginCallbacks)
                .setGoogleApiClient(googleApiClient)
    }

    @Provides
    @Singleton
    fun providesGoogleApiClient(context: Context, gso: GoogleSignInOptions): GoogleApiClient {
        return GoogleApiClient.Builder(context)
                .enableAutoManage(context as FragmentActivity, context as GoogleApiClient.OnConnectionFailedListener)
                .addApi(com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    @Provides
    @Singleton
    fun providesGoogleSignOptions(context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
    }
}