package com.rowland.delivery.merchant.application

import android.support.multidex.MultiDexApplication

import com.rowland.delivery.merchant.application.di.components.ApplicationComponent
import com.rowland.delivery.merchant.application.di.components.DaggerApplicationComponent
import com.rowland.delivery.merchant.application.di.modules.ContextModule


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
    }
}
