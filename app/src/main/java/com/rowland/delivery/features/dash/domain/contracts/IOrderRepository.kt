package com.rowland.delivery.features.dash.domain.contracts

import com.rowland.delivery.features.dash.domain.models.order.OrderData
import io.reactivex.Completable
import io.reactivex.Flowable

interface IOrderRepository {
    fun getOrders(userUID: String): Flowable<List<OrderData>>
    fun updateOrderStatus(status: String, firestoreUid: String): Completable
}