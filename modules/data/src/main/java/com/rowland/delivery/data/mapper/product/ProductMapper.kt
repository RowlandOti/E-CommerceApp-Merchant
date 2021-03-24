/*
 * Copyright 2021 Otieno Rowland
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

package com.rowland.delivery.data.mapper.product

import com.rowland.delivery.data.mapper.Mapper
import com.rowland.delivery.data.model.product.ProductPojo
import com.rowland.delivery.domain.models.product.ProductEntity
import javax.inject.Inject

open class ProductMapper @Inject constructor() : Mapper<ProductPojo, ProductEntity> {

    override fun mapFromData(type: ProductPojo): ProductEntity {
        val product = ProductEntity()
        product.id = type.id
        product.name = type.name
        product.description = type.description
        product.price = type.price
        product.itemCode = type.itemCode
        product.merchantCode = type.merchantCode
        product.itemQuantity = type.itemQuantity
        product.unitsOfMeasure = type.unitsOfMeasure
        product.saleQuantity = type.saleQuantity
        product.imageUrl = type.imageUrl
        product.imageUrls = type.imageUrls
        product.createdAt = type.createdAt
        product.updatedAt = type.updatedAt
        product.deletedAt = type.deletedAt
        product.firestoreUid = type.firestoreUid
        return product
    }

    override fun mapToData(type: ProductEntity): ProductPojo {
        val product = ProductPojo()
        product.id = type.id
        product.name = type.name
        product.description = type.description
        product.price = type.price
        product.itemCode = type.itemCode
        product.merchantCode = type.merchantCode
        product.itemQuantity = type.itemQuantity
        product.unitsOfMeasure = type.unitsOfMeasure
        product.saleQuantity = type.saleQuantity
        product.imageUrl = type.imageUrl
        product.imageUrls = type.imageUrls
        product.createdAt = type.createdAt
        product.updatedAt = type.updatedAt
        product.deletedAt = type.deletedAt
        product.firestoreUid = type.firestoreUid
        return product
    }
}
