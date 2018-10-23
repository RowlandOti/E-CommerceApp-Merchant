package com.rowland.delivery.data.source.product

import com.rowland.delivery.data.contracts.product.IProductCacheSource
import com.rowland.delivery.data.contracts.product.IProductDataStore
import javax.inject.Inject

class ProductDataStoreFactory @Inject constructor(
        private val productCacheSource: IProductCacheSource,
        private val productCacheDataStore: ProductCacheDataStore,
        private val productRemoteDataStore: ProductRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    fun retrieveDataStore(isCached: Boolean): IProductDataStore {
        if (isCached && !productCacheSource.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
   fun retrieveCacheDataStore(): IProductDataStore {
        return productCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    fun retrieveRemoteDataStore(): IProductDataStore {
        return productRemoteDataStore
    }
}