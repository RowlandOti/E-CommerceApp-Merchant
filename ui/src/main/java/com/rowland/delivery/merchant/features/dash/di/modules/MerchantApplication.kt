package com.rowland.delivery.merchant.features.dash.di.modules

import androidx.multidex.MultiDexApplication
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Rowland on 4/30/2018.
 */

@HiltAndroidApp
class MerchantApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // ToDo: Disable logging in production
        FirebaseFirestore.setLoggingEnabled(true)
    }
}
