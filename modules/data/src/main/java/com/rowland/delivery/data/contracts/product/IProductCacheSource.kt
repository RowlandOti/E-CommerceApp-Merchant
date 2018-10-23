package com.rowland.delivery.data.contracts.product

import com.rowland.delivery.data.model.product.ProductPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface IProductCacheSource {
    fun createProduct(productPojo: ProductPojo,productCategory: String, userUid: String): Single<ProductPojo>
    fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable
    fun deleteProduct(productUid: String): Completable
    fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductPojo>>
    fun saveToCache(products: List<ProductPojo>): Completable
    fun clearFromCache(): Completable
    fun isCached(): Single<Boolean>
    fun setLastCacheTime(lastCache: Long)
    fun isExpired(): Boolean
}