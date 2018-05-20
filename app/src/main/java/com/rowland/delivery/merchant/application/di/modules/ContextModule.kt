package com.rowland.delivery.merchant.application.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 4/30/2018.
 */

@Module
class ContextModule(private val context: Context) {

    @Provides
    fun providesContext(): Context {
        return this.context
    }
}
