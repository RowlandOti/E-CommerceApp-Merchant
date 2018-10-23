package com.rowland.delivery.features.dash.domain.usecases.product

import com.rowland.delivery.features.dash.domain.contracts.IProductRepository
import com.rowland.delivery.features.dash.domain.models.product.ProductEntity
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

class CreateProductUseCase @Inject
constructor(private val productRepository: IProductRepository) {

    fun createProduct(productEntity: ProductEntity, productCategory: String, userUid: String): Single<ProductEntity> {
        return this.productRepository.createProduct(productEntity, userUid, productCategory)
    }
}
