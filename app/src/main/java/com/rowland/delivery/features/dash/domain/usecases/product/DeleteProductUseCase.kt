package com.rowland.delivery.features.dash.domain.usecases.product

import com.rowland.delivery.features.dash.data.model.product.ProductRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteProductsUseCase @Inject
constructor(private val productRepository: ProductRepository) {

    fun deleteProduct(productUid: String): Completable {
        return this.productRepository.deleteProduct(productUid)
    }
}