package com.rowland.delivery.merchant.application.di.components

import android.content.Context

import com.rowland.delivery.merchant.application.di.modules.ContextModule

import dagger.Component

/**
 * Created by Rowland on 4/30/2018.
 */

@Component(modules = arrayOf(ContextModule::class))
interface ApplicationComponent {

    val context: Context
}
