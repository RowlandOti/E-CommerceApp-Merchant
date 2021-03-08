package com.rowland.delivery.merchant

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by Rowland on 4/30/2018.
 */

@HiltAndroidApp
class MerchantApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { e -> Log.d(MerchantApplication::class.java.simpleName, e.toString()) }
        FirebaseFirestore.setLoggingEnabled(BuildConfig.DEBUG)
    }
}
