package com.rowland.delivery.merchant.features.dash.di.modules

import androidx.multidex.MultiDexApplication
import com.google.firebase.firestore.FirebaseFirestore
import com.rowland.delivery.merchant.di.components.ApplicationComponent
import com.rowland.delivery.merchant.di.components.DaggerApplicationComponent
import com.rowland.delivery.merchant.di.modules.ContextModule




/**
 * Created by Rowland on 4/30/2018.
 */

class MerchantApplication : MultiDexApplication() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(ContextModule(this))
                .build()
        // ToDo: Disable logging in production
        FirebaseFirestore.setLoggingEnabled(true)
    }
}
