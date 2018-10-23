package com.rowland.delivery.data.contracts.order

import com.rowland.delivery.data.model.order.OrderDataPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface IOrderCacheSource {
    fun createOrder(order: OrderDataPojo, userUid: String): Single<OrderDataPojo>
    fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable
    fun deleteOrder(orderUid: String): Completable
    fun loadOrders(userUid: String): Flowable<List<OrderDataPojo>>
    fun saveToCache(Orders: List<OrderDataPojo>): Completable
    fun clearFromCache(): Completable
    fun isCached(): Single<Boolean>
    fun setLastCacheTime(lastCache: Long)
    fun isExpired(): Boolean
}