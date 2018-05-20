package com.rowland.delivery.features.dash.domain.usecases.product

import com.rowland.delivery.features.dash.data.model.product.ProductRepository
import com.rowland.delivery.features.dash.domain.models.product.Product
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

class LoadProductsUseCase @Inject
constructor(private val productRepository: ProductRepository) {

    fun loadProducts(userUID: String, productCategory: String): Flowable<List<Product>> {
        return this.productRepository.getProducts(userUID, productCategory)
    }
}
