package com.rowland.delivery.domain.usecases.order

import com.rowland.delivery.domain.contracts.IOrderRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.usecases.CompletableUseCase
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject


class UpdateOrderUseCase @Inject
constructor(private val orderRepository: IOrderRepository, threadExecutor: IThreadExecutor, postExecutionThread: IPostExecutionThread): CompletableUseCase<UpdateOrderUseCase.Params>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Params): Completable {
        return this.orderRepository.updateOrder(params.orderUpdateFields, params.orderUID)
    }

    fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUID: String): Completable {
        return this.orderRepository.updateOrder(orderUpdateFields, orderUID)
    }

    data class Params constructor(val orderUpdateFields: HashMap<String, Any>,val orderUID: String) {
        companion object {
            fun forOrder(orderUpdateFields: HashMap<String, Any>,orderUID: String): Params {
                return Params(orderUpdateFields, orderUID)
            }
        }
    }
}