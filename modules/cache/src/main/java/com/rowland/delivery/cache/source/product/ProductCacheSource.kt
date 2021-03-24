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

package com.rowland.delivery.cache.source.product

import com.rowland.delivery.data.contracts.product.IProductCacheSource
import com.rowland.delivery.data.model.product.ProductPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableEmpty
import java.util.HashMap
import javax.inject.Inject

class ProductCacheSource @Inject constructor() : IProductCacheSource {

    override fun createProduct(
        productPojo: ProductPojo,
        productCategory: String,
        userUid: String
    ): Single<ProductPojo> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteProduct(productUid: String): Completable {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductPojo>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun saveToCache(products: List<ProductPojo>): Completable {
        return CompletableEmpty.complete()
    }

    override fun clearFromCache(): Completable {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun isCached(): Single<Boolean> {
        return Single.just(false)
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(): Boolean {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
