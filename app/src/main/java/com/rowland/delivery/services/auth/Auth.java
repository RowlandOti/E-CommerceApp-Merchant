package com.rowland.delivery.services.auth;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;
import com.rowland.delivery.services.auth.models.User;

import java.util.Map;

/**
 * Created by Rowland on 5/1/2018.
 */

public abstract class Auth {

    public abstract void login();
    public abstract boolean logout(Context context);
    public abstract void register();
    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public interface AuthLoginCallbacks {

        void onLoginSuccess();

        void onLoginFailure(AuthException e);

        Map<String, String> doEmailLogin();

        Map<String, String> doEmailRegister();
    }
}
