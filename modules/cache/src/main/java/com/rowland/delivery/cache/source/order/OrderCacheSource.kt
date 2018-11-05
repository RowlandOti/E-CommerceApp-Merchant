package com.rowland.delivery.cache.source.order

import com.rowland.delivery.data.contracts.order.IOrderCacheSource
import com.rowland.delivery.data.model.order.OrderDataPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableEmpty
import java.util.*
import javax.inject.Inject

class OrderCacheSource @Inject constructor() : IOrderCacheSource {
    override fun createOrder(order: OrderDataPojo, userUid: String): Single<OrderDataPojo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteOrder(orderUid: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadOrders(userUid: String): Flowable<List<OrderDataPojo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveToCache(Orders: List<OrderDataPojo>): Completable {
        return CompletableEmpty.complete()
    }

    override fun clearFromCache(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCached(): Single<Boolean> {
        return Single.just(false)
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}