package com.rowland.delivery.domain.usecases.product

import com.rowland.delivery.domain.contracts.IProductRepository
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject

/**
 * Created by Rowland on 10/19/2018.
 */

class UpdateProductUseCase @Inject
constructor(private val productRepository: IProductRepository) {

    fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable {
        return this.productRepository.updateProduct(productUpdateFields, productUid)
    }
}