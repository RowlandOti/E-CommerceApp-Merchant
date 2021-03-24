/*
 * Copyright 2021 Otieno Rowland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.data.source.category

import com.rowland.delivery.data.contracts.category.ICategoryCacheSource
import com.rowland.delivery.data.contracts.category.ICategoryDataStore

import javax.inject.Inject

open class CategoryDataStoreFactory @Inject constructor(
    private val cacheSource: ICategoryCacheSource,
    private val cacheDataStore: CategoryCacheDataStore,
    private val remoteDataStore: CategoryRemoteDataStore
) {

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
