package com.rowland.delivery.merchant.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

class ApplicationModule {
}

@Module
class ContextModule(private val context: Context) {

    @Provides
    fun providesContext(): Context {
        return this.context
    }
}