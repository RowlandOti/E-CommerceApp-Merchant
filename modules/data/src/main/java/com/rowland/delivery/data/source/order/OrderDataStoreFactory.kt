package com.rowland.delivery.data.source.order


import com.rowland.delivery.data.contracts.order.IOrderCacheSource
import com.rowland.delivery.data.contracts.order.IOrderDataStore

import javax.inject.Inject

class OrderDataStoreFactory @Inject constructor(
        private val orderCacheSource: IOrderCacheSource,
        private val orderCacheDataStore: OrderCacheDataStore,
        private val orderRemoteDataStore: OrderRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    fun retrieveDataStore(isCached: Boolean): IOrderDataStore {
        if (isCached && !orderCacheSource.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    fun retrieveCacheDataStore(): IOrderDataStore {
        return orderCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    fun retrieveRemoteDataStore(): IOrderDataStore {
        return orderRemoteDataStore
    }
}