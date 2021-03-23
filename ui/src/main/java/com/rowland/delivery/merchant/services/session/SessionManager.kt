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
