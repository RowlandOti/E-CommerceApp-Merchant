package com.rowland.delivery.features.dash.domain.contracts

import com.rowland.delivery.features.dash.domain.models.product.ProductEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface IProductRepository {
    fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductEntity>>
    fun createProduct(productEntity: ProductEntity, productCategory: String, userUid: String): Single<ProductEntity>
    fun updateProduct(productUpdateFields: HashMap<String, Any>, firestoreUid: String): Single<ProductEntity>
    fun deleteProduct(productUid: String): Completable
}
