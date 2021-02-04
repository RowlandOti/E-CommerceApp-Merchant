package com.rowland.delivery.merchant.services.session


import com.rowland.delivery.merchant.services.sharedpreferences.SharedPreferencesManager
import com.rowland.delivery.merchant.utilities.AppConstants
import java.util.*
import javax.inject.Inject

/**
 * Created by Rowland on 9/11/17.
 */

class SessionManager @Inject
constructor(private val preferencesManager: SharedPreferencesManager) {

    val isLoggedIn: Boolean
        get() = preferencesManager.getBoolean(AppConstants.KEY_IS_LOGGEDIN)

    val authToken: String?
        get() = preferencesManager.getString(AppConstants.KEY_TOKEN)

    fun setLogin(token: String) {

        preferencesManager.putBoolean(AppConstants.KEY_IS_LOGGEDIN, true)
                .putString(AppConstants.KEY_TOKEN, token)
                .putLong(AppConstants.KEY_TIME, Date().time)
    }

    fun logout() {
        preferencesManager.remove(AppConstants.KEY_IS_LOGGEDIN)
                .remove(AppConstants.KEY_TOKEN)
                .remove(AppConstants.KEY_TIME)
    }

    fun shouldLogout(): Boolean {
        val diffMSec = Date().time - preferencesManager.getLong(AppConstants.KEY_TIME)
        val diffHours = (diffMSec / (1000 * 60 * 60)).toInt()

        return if (diffHours >= 23) {
            preferencesManager.putBoolean(AppConstants.KEY_IS_LOGGEDIN, false)
                .putString(AppConstants.KEY_TOKEN, "")
                .putLong(AppConstants.KEY_TIME, 0)

            true
        } else {
            false
        }
    }
}
