package com.rowland.delivery.merchant.features.auth.di.components


import com.rowland.delivery.merchant.features.auth.di.modules.AuthModule
import com.rowland.delivery.merchant.features.auth.ui.AuthActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rowland on 5/1/2018.
 */

@Component(modules = arrayOf(AuthModule::class))
@Singleton
internal interface AuthComponent {
    fun injectAuthActivity(activity: AuthActivity)
}
