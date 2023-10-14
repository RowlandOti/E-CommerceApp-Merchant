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

package com.rowland.delivery.merchant.services.session

import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.data.contracts.IPreferencesManager
import com.rowland.delivery.merchant.utilities.AppConstants
import java.util.Date
import javax.inject.Inject

/**
 * Created by Rowland on 9/11/17.
 */

class SessionManager @Inject
constructor(
    private val preferencesManager: IPreferencesManager,
    private val firebaseAuth: FirebaseAuth
) :
    FirebaseAuth.AuthStateListener {

    private val onSignOutListeners: MutableList<OnSignOutListener> = mutableListOf()

    init {
        firebaseAuth.addAuthStateListener(this)
    }

    interface OnSignOutListener {

        fun onSignOut()
    }

    fun addOnSignOutListener(onSignOutListener: OnSignOutListener) {
        onSignOutListeners.add(onSignOutListener)
    }

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        firebaseAuth.currentUser?.let { } ?: run {
            preferencesManager.remove(AppConstants.KEY_IS_LOGGEDIN)
                .remove(AppConstants.KEY_TOKEN)
                .remove(AppConstants.KEY_TIME)

            for (listener in onSignOutListeners) {
                listener.onSignOut()
            }
        }
    }

    val isLoggedIn: Boolean
        get() = preferencesManager.getBoolean(AppConstants.KEY_IS_LOGGEDIN, false)

    val authToken: String?
        get() = preferencesManager.getString(AppConstants.KEY_TOKEN, "")

    fun setLogin(token: String) {
        preferencesManager.set(AppConstants.KEY_IS_LOGGEDIN, true)
            .set(AppConstants.KEY_TOKEN, token)
            .set(AppConstants.KEY_TIME, Date().time)
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun shouldLogout(): Boolean {
        val diffMSec = Date().time - preferencesManager.getLong(AppConstants.KEY_TIME, 0L)
        val diffHours = (diffMSec / (1000 * 60 * 60)).toInt()

        return if (diffHours >= 23) {
            preferencesManager.set(AppConstants.KEY_IS_LOGGEDIN, false)
                .set(AppConstants.KEY_TOKEN, "")
                .set(AppConstants.KEY_TIME, 0)

            true
        } else {
            false
        }
    }
}
