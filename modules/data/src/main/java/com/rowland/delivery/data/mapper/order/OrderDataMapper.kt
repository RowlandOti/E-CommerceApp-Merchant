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

package com.rowland.delivery.data.mapper.order

import com.rowland.delivery.data.mapper.Mapper
import com.rowland.delivery.data.model.order.OrderDataPojo
import com.rowland.delivery.data.model.order.OrderItemPojo
import com.rowland.delivery.domain.models.order.OrderDataEntity
import com.rowland.delivery.domain.models.order.OrderItemEntity
import javax.inject.Inject

open class OrderDataMapper @Inject constructor() : Mapper<OrderDataPojo, OrderDataEntity> {

    companion object {
        fun mapOrderItemFromData(type: OrderItemPojo): OrderItemEntity {
            val orderItem = OrderItemEntity()
            orderItem.itemCode = type.itemCode
            orderItem.title = type.title
            orderItem.imageUrl = type.imageUrl
            orderItem.itemQuantity = type.itemQuantity
            orderItem.price = type.price
            orderItem.itemTotal = type.itemTotal
            orderItem.foreign = type.foreign

            return orderItem
        }

        fun mapOrderItemToData(type: OrderItemEntity): OrderItemPojo {
            val orderItem = OrderItemPojo()
            orderItem.itemCode = type.itemCode
            orderItem.title = type.title
            orderItem.imageUrl = type.imageUrl
            orderItem.itemQuantity = type.itemQuantity
            orderItem.price = type.price
            orderItem.itemTotal = type.itemTotal
            orderItem.foreign = type.foreign

            return orderItem
        }
    }

    override fun mapFromData(type: OrderDataPojo): OrderDataEntity {
        val product = OrderDataEntity()
        product.id = type.id
        product.orderRef = type.orderRef
        product.status = type.status
        product.createdAt = type.createdAt
        product.updatedAt = type.updatedAt
        product.itemIds = type.itemIds
        product.phone = type.phone
        product.name = type.name
        product.address = type.address
        product.lng = type.lng
        product.lat = type.lat
        product.orderDescription = type.orderDescription

        val orderItems = mutableListOf<OrderItemEntity>()
        type.items!!.map { orderItems.add(mapOrderItemFromData(it)) }
        product.items = orderItems

        product.deliveryFee = type.deliveryFee
        product.orderItemsQuantity = type.orderItemsQuantity
        product.orderSubTotal = type.orderSubTotal
        product.orderTotal = type.orderTotal
        product.firestoreUid = type.firestoreUid

        return product
    }

    override fun mapToData(type: OrderDataEntity): OrderDataPojo {
        val product = OrderDataPojo()
        product.id = type.id
        product.orderRef = type.orderRef
        product.status = type.status
        product.createdAt = type.createdAt
        product.updatedAt = type.updatedAt
        product.itemIds = type.itemIds
        product.phone = type.phone
        product.name = type.name
        product.address = type.address
        product.lng = type.lng
        product.lat = type.lat
        product.orderDescription = type.orderDescription

        val orderItems = mutableListOf<OrderItemPojo>()
        type.items!!.map { orderItems.add(mapOrderItemToData(it)) }
        product.items = orderItems

        product.deliveryFee = type.deliveryFee
        product.orderItemsQuantity = type.orderItemsQuantity
        product.orderSubTotal = type.orderSubTotal
        product.orderTotal = type.orderTotal
        product.firestoreUid = type.firestoreUid

        return product
    }
}
