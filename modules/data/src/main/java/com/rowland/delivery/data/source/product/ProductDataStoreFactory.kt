/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
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

package com.rowland.delivery.data.source.product

import com.rowland.delivery.data.contracts.product.IProductCacheSource
import com.rowland.delivery.data.contracts.product.IProductDataStore
import javax.inject.Inject

class ProductDataStoreFactory @Inject constructor(
    private val productCacheSource: IProductCacheSource,
    private val productCacheDataStore: ProductCacheDataStore,
    private val productRemoteDataStore: ProductRemoteDataStore
) {

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
