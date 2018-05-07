package com.rowland.delivery.services.auth.di.modules;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rowland.delivery.merchant.R;
import com.rowland.delivery.merchant.application.di.modules.ContextModule;
import com.rowland.delivery.services.auth.AuthConfig;
import com.rowland.delivery.services.auth.EmailAuth;
import com.rowland.delivery.services.auth.GoogleAuth;
import com.rowland.delivery.services.auth.Auth;
import com.rowland.delivery.services.firebase.modules.FirebaseModule;
import com.rowland.delivery.services.session.SessionManager;
import com.rowland.delivery.services.session.di.modules.SessionModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 5/1/2018.
 */

@Module(includes = {ContextModule.class, SessionModule.class, FirebaseModule.class})
public class AuthModule {

    @Provides
    @Singleton
    @Named("email_login")
    public Auth providesEmailAuth(AuthConfig authConfig, FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestore, SessionManager sessionManager) {
        return new EmailAuth(authConfig, firebaseAuth,firebaseFirestore, sessionManager);
    }

    @Provides
    @Singleton
    @Named("google_login")
    public Auth providesGoogleAuth(AuthConfig authConfig, FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestore, SessionManager sessionManager) {
        return new GoogleAuth(authConfig, firebaseAuth,firebaseFirestore, sessionManager);
    }

    @Provides
    @Singleton
    public AuthConfig providesSocialAuthConfig(Context context, GoogleApiClient googleApiClient) {
        return new AuthConfig((Activity) context, (Auth.AuthLoginCallbacks) context)
                .setGoogleApiClient(googleApiClient);
    }

    @Provides
    @Singleton
    public GoogleApiClient providesGoogleApiClient(Context context, GoogleSignInOptions gso) {
        return new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, (GoogleApiClient.OnConnectionFailedListener) context)
                .addApi(com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Provides
    @Singleton
    public GoogleSignInOptions providesGoogleSignOptions(Context context) {
        return new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
    }
}
