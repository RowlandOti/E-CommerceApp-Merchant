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

package com.rowland.delivery.merchant.services.sharedpreferences

import android.content.SharedPreferences

/**
 * Created by Rowland on 4/30/2018.
 */

class SharedPreferencesManager(private val mSharedPreferences: SharedPreferences) {

    fun remove(key: String): SharedPreferencesManager {
        mSharedPreferences.edit().remove(key).apply()
        return this
    }

    fun putInt(key: String, data: Int): SharedPreferencesManager {
        mSharedPreferences.edit().putInt(key, data).apply()
        return this
    }

    fun getInt(key: String): Int {
        return mSharedPreferences.getInt(key, 0)
    }

    fun putLong(key: String, data: Long) {
        mSharedPreferences.edit().putLong(key, data).apply()
    }

    fun getLong(key: String): Long {
        return mSharedPreferences.getLong(key, 0)
    }

    fun putBoolean(key: String, data: Boolean): SharedPreferencesManager {
        mSharedPreferences.edit().putBoolean(key, data).apply()
        return this
    }

    fun getBoolean(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, false)
    }

    fun putString(key: String, data: String): SharedPreferencesManager {
        mSharedPreferences.edit().putString(key, data).apply()
        return this
    }

    fun getString(key: String): String? {
        return mSharedPreferences.getString(key, "")
    }
}
