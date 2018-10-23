package com.rowland.delivery.data.source.category


import com.rowland.delivery.data.contracts.category.ICategoryCacheSource
import com.rowland.delivery.data.contracts.category.ICategoryDataStore

import javax.inject.Inject

open class CategoryDataStoreFactory @Inject constructor(
        private val cacheSource: ICategoryCacheSource,
        private val cacheDataStore: CategoryCacheDataStore,
        private val remoteDataStore: CategoryRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): ICategoryDataStore {
        if (isCached && !cacheSource.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): ICategoryDataStore {
        return cacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): ICategoryDataStore {
        return remoteDataStore
    }
}