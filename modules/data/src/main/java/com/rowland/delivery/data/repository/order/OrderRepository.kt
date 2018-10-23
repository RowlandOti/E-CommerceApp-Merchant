package com.rowland.delivery.data.repository.order

import com.rowland.delivery.data.mapper.order.OrderDataMapper
import com.rowland.delivery.data.model.order.OrderDataPojo
import com.rowland.delivery.data.source.order.OrderDataStoreFactory
import com.rowland.delivery.domain.contracts.IOrderRepository
import com.rowland.delivery.domain.models.order.OrderDataEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by Rowland on 5/8/2018.
 */

class OrderRepository @Inject constructor(private val dataStoreFactory: OrderDataStoreFactory,
                                          private val mapper: OrderDataMapper) : IOrderRepository {
    override fun clearFromCache(): Completable {
        return dataStoreFactory.retrieveCacheDataStore().clearFromCache()
    }

    override fun saveToCache(orders: List<OrderDataEntity>): Completable {
        val orderDataPojos = mutableListOf<OrderDataPojo>()
        orders.map { orderDataPojos.add(mapper.mapToData(it)) }
        return dataStoreFactory.retrieveCacheDataStore().saveToCache(orderDataPojos)
    }

    override fun loadOrders(userUid: String): Flowable<List<OrderDataEntity>> {
        // Get all orders serving by given merchant
        return dataStoreFactory.retrieveCacheDataStore().isCached()
                .flatMapPublisher {
                    dataStoreFactory.retrieveDataStore(it).loadOrders(userUid)
                }
                .flatMap {
                    Flowable.just(it.map { mapper.mapFromData(it) })
                }
                .flatMap {
                    saveToCache(it).toSingle { it }.toFlowable()
                }
    }

    override fun createOrder(orderDataEntity: OrderDataEntity, userUid: String): Single<OrderDataEntity> {
        return dataStoreFactory.retrieveDataStore(false).createOrder(mapper.mapToData(orderDataEntity), userUid)
                .map { mapper.mapFromData(it) }
    }

    override fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable {
        return dataStoreFactory.retrieveDataStore(false).updateOrder(orderUpdateFields, orderUid)
    }

    override fun deleteOrder(orderUid: String): Completable {
        return dataStoreFactory.retrieveDataStore(false).deleteOrder(orderUid)
    }


    /*public Flowable<List<OrderDataPojo>> getOrders(String userUID) {
        CollectionReference categoryCollectionRef = mFirebaseFirestone.collection("orders");
        Query query = categoryCollectionRef.whereEqualTo(String.format("merchants.%s", userUID), true);
        return RxFirestore.observeQueryRef(query, OrderDataPojo.class);
    }*/
}
