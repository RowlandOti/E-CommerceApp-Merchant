package com.rowland.delivery.features.dash.domain.usecases.order

import com.rowland.delivery.features.dash.domain.contracts.IOrderRepository
import com.rowland.delivery.features.dash.domain.models.order.OrderData
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Rowland on 5/8/2018.
 */

class LoadOrdersUseCase @Inject
constructor(private val orderRepository: IOrderRepository) {

    fun loadOrders(userUID: String): Flowable<List<OrderData>> {
        return this.orderRepository.getOrders(userUID)
    }

    fun updateOrderStatus(status: String, firestoreUid: String): Completable {
        return this.orderRepository.updateOrderStatus(status, firestoreUid)
    }
}
