package com.rowland.delivery.merchant.features.auth

import android.app.Activity

import com.google.android.gms.common.api.GoogleApiClient

/**
 * Created by Rowland on 5/1/2018.
 */

class AuthConfig(val activity: Activity, val callback: Auth.AuthLoginCallbacks) {

    internal var googleApiClient: GoogleApiClient? = null

    fun getGoogleApiClient(): GoogleApiClient? {
        return this.googleApiClient
    }

    fun setGoogleApiClient(googleApiClient: GoogleApiClient): AuthConfig {
        this.googleApiClient = googleApiClient
        return this
    }


}
