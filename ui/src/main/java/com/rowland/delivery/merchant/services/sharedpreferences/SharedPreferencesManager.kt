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
