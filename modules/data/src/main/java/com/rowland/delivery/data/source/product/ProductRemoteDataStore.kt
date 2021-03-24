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

import com.rowland.delivery.data.contracts.product.IProductDataStore
import com.rowland.delivery.data.contracts.product.IProductRemoteSource
import com.rowland.delivery.data.model.product.ProductPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.HashMap
import javax.inject.Inject

class ProductRemoteDataStore @Inject constructor(private val dataSource: IProductRemoteSource) : IProductDataStore {

    override fun saveToCache(products: List<ProductPojo>): Completable {
        throw UnsupportedOperationException()
    }

    override fun clearFromCache(): Completable {
        throw UnsupportedOperationException()
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

    override fun createProduct(
        productPojo: ProductPojo,
        productCategory: String,
        userUid: String
    ): Single<ProductPojo> {
        return dataSource.createProduct(productPojo, productCategory, userUid)
    }

    override fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable {
        return dataSource.updateProduct(productUpdateFields, productUid)
    }

    override fun deleteProduct(productUid: String): Completable {
        return dataSource.deleteProduct(productUid)
    }

    override fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductPojo>> {
        return dataSource.loadProducts(userUid, productCategory)
    }
}
