package com.rowland.delivery.merchant.executor

import com.rowland.delivery.domain.executor.IPostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

import javax.inject.Inject

/**
 * MainThread (UI Thread) implementation based on a [Scheduler]
 * which will execute actions on the Android UI thread
 */
class UiThread @Inject internal constructor() : IPostExecutionThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

}