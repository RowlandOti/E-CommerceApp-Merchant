package com.rowland.delivery.features.dash.domain.usecases.product

import com.rowland.delivery.features.dash.data.model.product.ProductRepository
import com.rowland.delivery.features.dash.domain.models.product.Product
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Created by Rowland on 10/19/2018.
 */

class UpdateProductUseCase @Inject
constructor(private val productRepository: ProductRepository) {

    fun updateProduct(productUpdateFields: HashMap<String, Any>, firestoreUid: String): Single<Product> {
        return this.productRepository.updateProduct(productUpdateFields, firestoreUid)
    }
}