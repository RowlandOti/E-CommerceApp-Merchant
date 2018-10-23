package com.rowland.delivery.domain.usecases.order

import com.rowland.delivery.domain.contracts.IOrderRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.models.order.OrderDataEntity
import com.rowland.delivery.domain.usecases.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Rowland on 5/8/2018.
 */

class LoadOrdersUseCase @Inject
constructor(private val orderRepository: IOrderRepository, threadExecutor: IThreadExecutor, postExecutionThread: IPostExecutionThread): FlowableUseCase<List<OrderDataEntity>, LoadOrdersUseCase.Params>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Flowable<List<OrderDataEntity>> {
        return this.orderRepository.loadOrders(params!!.userUID)
    }

    data class Params constructor(val userUID: String) {
        companion object {
            fun forOrders(userUID: String): Params {
                return Params(userUID)
            }
        }
    }
}
