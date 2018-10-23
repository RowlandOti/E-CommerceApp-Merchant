package com.rowland.delivery.merchant.di.modules

import androidx.annotation.UiThread
import com.rowland.delivery.domain.executor.IPostExecutionThread
import dagger.Binds
import dagger.Module

/**
 * Module that provides all dependencies from the mobile package/layer and injects all activities.
 */
@Module
abstract class UiModule {
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): IPostExecutionThread
}