package com.rowland.delivery.merchant.features.auth

import android.content.Context
import android.content.Intent

/**
 * Created by Rowland on 5/1/2018.
 */

abstract class Auth {

    abstract fun login()
    abstract fun logout(context: Context): Boolean
    abstract fun register()
    abstract fun passwordReset()
    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)

    interface AuthLoginCallbacks {

        fun onLoginSuccess()

        fun onLoginFailure(e: AuthException)

        fun doEmailLogin(): Map<String, String>

        fun doEmailRegister(): Map<String, String>
    }
}
