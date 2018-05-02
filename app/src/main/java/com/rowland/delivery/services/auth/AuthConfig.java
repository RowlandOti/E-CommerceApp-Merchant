package com.rowland.delivery.services.auth;

import android.app.Activity;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Rowland on 5/1/2018.
 */

public class AuthConfig {

    private GoogleApiClient googleApiClient;
    private Activity activity;
    private Auth.AuthLoginCallbacks callback;


    public AuthConfig(Activity context, Auth.AuthLoginCallbacks callback) {
        this.activity = context;
        this.callback = callback;
    }

    public Activity getActivity() {
        return activity;
    }

    public Auth.AuthLoginCallbacks getCallback() {
        return callback;
    }

    public GoogleApiClient getGoogleApiClient() {
        return this.googleApiClient;
    }

    public AuthConfig setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
        return this;
    }


}
