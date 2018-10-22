package com.rowland.delivery.features.dash.domain.contracts

import com.rowland.delivery.features.dash.domain.models.product.Product
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface IProductRepository {
    fun loadProducts(userUid: String, productCategory: String): Flowable<List<Product>>
    fun createProduct(product: Product, productCategory: String, userUid: String): Single<Product>
    fun updateProduct(productUpdateFields: HashMap<String, Any>, firestoreUid: String): Single<Product>
    fun deleteProduct(productUid: String): Completable
}
