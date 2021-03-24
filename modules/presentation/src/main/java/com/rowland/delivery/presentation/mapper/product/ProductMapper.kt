/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.presentation.mapper.product

import com.rowland.delivery.domain.models.product.ProductEntity
import com.rowland.delivery.presentation.mapper.IMapper
import com.rowland.delivery.presentation.model.product.ProductModel
import javax.inject.Inject

open class ProductMapper @Inject constructor() : IMapper<ProductModel, ProductEntity> {
    override fun mapToView(type: ProductEntity): ProductModel {
        val productModel = ProductModel()
        productModel.id = type.id
        productModel.name = type.name
        productModel.description = type.description
        productModel.price = type.price
        productModel.itemCode = type.itemCode
        productModel.merchantCode = type.merchantCode
        productModel.itemQuantity = type.itemQuantity
        productModel.unitsOfMeasure = type.unitsOfMeasure
        productModel.saleQuantity = type.saleQuantity
        productModel.imageUrl = type.imageUrl
        productModel.imageUrls = type.imageUrls
        productModel.createdAt = type.createdAt
        productModel.updatedAt = type.updatedAt
        productModel.deletedAt = type.deletedAt
        productModel.firestoreUid = type.firestoreUid
        return productModel
    }
}
