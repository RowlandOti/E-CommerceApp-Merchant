package com.rowland.delivery.services.sharedpreferences;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by Rowland on 4/30/2018.
 */

public class SharedPreferencesManager {

    private SharedPreferences mSharedPreferences;

    public SharedPreferencesManager(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public SharedPreferencesManager remove(String key) {
        mSharedPreferences.edit().remove(key).apply();
        return this;
    }

    public SharedPreferencesManager putInt(String key, int data) {
        mSharedPreferences.edit().putInt(key,data).apply();
        return this;
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key,0);
    }

    public void putLong(String key, long data) {
        mSharedPreferences.edit().putLong(key,data).apply();
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public SharedPreferencesManager putBoolean(String key, boolean data) {
        mSharedPreferences.edit().putBoolean(key,data).apply();
        return this;
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public SharedPreferencesManager putString(String key, String data) {
        mSharedPreferences.edit().putString(key,data).apply();
        return this;
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }
}
