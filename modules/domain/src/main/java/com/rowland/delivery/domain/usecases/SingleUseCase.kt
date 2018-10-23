package com.rowland.delivery.domain.usecases

import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Single].
 * */

abstract class SingleUseCase<T, in Params> constructor(
        private val threadExecutor: IThreadExecutor,
        private val postExecutionThread: IPostExecutionThread) {

    private val subscription = Disposables.empty()

    /**
     * Builds a [Single] which will be used when the current [SingleUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params? = null): Single<T>

    /**
     * Executes the current use case.
     */
    open fun execute(params: Params? = null) : Single<T> {
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler) as Single<T>
    }
    /**
     * Unsubscribes from current [Disposable].
     */
    fun unsubscribe() {
        if (!subscription.isDisposed) {
            subscription.dispose()
        }
    }
}