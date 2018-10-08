package com.rowland.delivery.features.dash.domain.usecases.product

import com.rowland.delivery.features.dash.data.model.product.ProductRepository
import com.rowland.delivery.features.dash.domain.models.product.Product
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

class CreateProductUseCase @Inject
constructor(private val productRepository: ProductRepository) {

    fun createProduct(product: Product, productCategory: String, userUid: String): Single<Product> {
        return this.productRepository.createProduct(product, userUid, productCategory)
    }
}