package com.rowland.delivery.data.source.order

import com.rowland.delivery.data.contracts.order.IOrderCacheSource
import com.rowland.delivery.data.contracts.order.IOrderDataStore
import com.rowland.delivery.data.model.order.OrderDataPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class OrderCacheDataStore @Inject constructor(private val dataSource: IOrderCacheSource) : IOrderDataStore {

    override fun saveToCache(orders: List<OrderDataPojo>): Completable {
        return dataSource.saveToCache(orders)
    }

    override fun isCached(): Single<Boolean> {
        return dataSource.isCached()
    }

    override fun createOrder(order: OrderDataPojo, userUid: String): Single<OrderDataPojo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable {
        return dataSource.updateOrder(orderUpdateFields, orderUid)
    }

    override fun deleteOrder(orderUid: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadOrders(userUid: String): Flowable<List<OrderDataPojo>> {
        return dataSource.loadOrders(userUid)
    }

    override fun clearFromCache(): Completable {
        return dataSource.clearFromCache()
    }
}
