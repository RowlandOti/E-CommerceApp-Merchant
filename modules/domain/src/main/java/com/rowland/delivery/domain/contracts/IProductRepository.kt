package com.rowland.delivery.domain.contracts

import com.rowland.delivery.domain.models.product.ProductEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface IProductRepository {
    fun clearFromCache(): Completable
    fun saveToCache(products: List<ProductEntity>): Completable
    fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductEntity>>
    fun createProduct(productEntity: ProductEntity, productCategory: String, userUid: String): Single<ProductEntity>
    fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable
    fun deleteProduct(productUid: String): Completable
}
