package com.rowland.delivery.domain.contracts

import com.rowland.delivery.domain.models.order.OrderDataEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IOrderRepository {
    fun clearFromCache(): Completable
    fun saveToCache(orders: List<OrderDataEntity>): Completable
    fun loadOrders(userUid: String): Flowable<List<OrderDataEntity>>
    fun createOrder(orderDataEntity: OrderDataEntity, userUid: String): Single<OrderDataEntity>
    fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable
    fun deleteOrder(orderUid: String): Completable
}