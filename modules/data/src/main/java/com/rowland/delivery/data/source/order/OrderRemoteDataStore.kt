package com.rowland.delivery.data.source.order

import com.rowland.delivery.data.contracts.order.IOrderDataStore
import com.rowland.delivery.data.contracts.order.IOrderRemoteSource
import com.rowland.delivery.data.model.order.OrderDataPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class OrderRemoteDataStore @Inject constructor(private val dataSource: IOrderRemoteSource) : IOrderDataStore {

    override fun saveToCache(orders: List<OrderDataPojo>): Completable {
        throw UnsupportedOperationException()
    }

    override fun clearFromCache(): Completable {
        throw UnsupportedOperationException()
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

    override fun createOrder(order: OrderDataPojo, userUid: String): Single<OrderDataPojo> {
        return dataSource.createOrder(order, userUid)
    }

    override fun deleteOrder(orderUid: String): Completable {
        throw UnsupportedOperationException()
    }

    override fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable {
        return dataSource.updateOrder(orderUpdateFields, orderUid)
    }

    override fun loadOrders(userUid: String): Flowable<List<OrderDataPojo>> {
        return dataSource.loadOrders(userUid)
    }
}
