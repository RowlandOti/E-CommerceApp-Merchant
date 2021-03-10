package com.rowland.delivery.merchant.features.auth

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignInClient

import com.google.android.gms.common.api.GoogleApiClient

/**
 * Created by Rowland on 5/1/2018.
 */

class AuthConfig(val activity: Activity, val callback: Auth.AuthLoginCallbacks) {

    private var googleSignInClient: GoogleSignInClient? = null

    fun getGoogleSignClient(): GoogleSignInClient? {
        return this.googleSignInClient
    }

    fun setGoogleSignInClient(googleSignInClient: GoogleSignInClient): AuthConfig {
        this.googleSignInClient = googleSignInClient
        return this
    }


}
