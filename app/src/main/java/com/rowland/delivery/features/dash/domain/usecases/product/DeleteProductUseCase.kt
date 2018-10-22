package com.rowland.delivery.features.dash.domain.usecases.product

import com.rowland.delivery.features.dash.domain.contracts.IProductRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteProductsUseCase @Inject
constructor(private val productRepository: IProductRepository) {

    fun deleteProduct(productUid: String): Completable {
        return this.productRepository.deleteProduct(productUid)
    }
}