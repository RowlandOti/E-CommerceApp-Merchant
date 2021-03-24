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

package com.rowland.delivery.remote.mapper.order

import com.rowland.delivery.data.model.order.OrderDataPojo
import com.rowland.delivery.data.model.order.OrderItemPojo
import com.rowland.delivery.remote.mapper.IMapper
import com.rowland.delivery.remote.model.order.OrderDataPayload
import com.rowland.delivery.remote.model.order.OrderItemPayload
import javax.inject.Inject

/**
 * Map a [OrderDataPayload] to and from a [OrderDataPojo] instance when data is moving between
 * this later and the Data layer
 */
class OrderDataMapper @Inject constructor() : IMapper<OrderDataPayload, OrderDataPojo> {

    companion object {

        fun mapOrderItemFromRemote(type: OrderItemPayload): OrderItemPojo {
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

        fun mapOrderItemToRemote(type: OrderItemPojo): OrderItemPayload {
            val orderItem = OrderItemPayload()
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

    /**
     * Map an instance of a [OrderDataPayload] to a [OrderDataPojo] model
     */
    override fun mapFromRemote(type: OrderDataPayload): OrderDataPojo {
        val orderData = OrderDataPojo()
        orderData.id = type.id
        orderData.orderRef = type.orderRef
        orderData.status = type.status
        orderData.createdAt = type.createdAt
        orderData.updatedAt = type.updatedAt
        orderData.itemIds = type.itemIds
        orderData.phone = type.phone
        orderData.name = type.name
        orderData.address = type.address
        orderData.lng = type.lng
        orderData.lat = type.lat
        orderData.orderDescription = type.orderDescription

        val orderItems = mutableListOf<OrderItemPojo>()
        type.items!!.map { orderItems.add(mapOrderItemFromRemote(it)) }
        orderData.items = orderItems

        orderData.deliveryFee = type.deliveryFee
        orderData.orderItemsQuantity = type.orderItemsQuantity
        orderData.orderSubTotal = type.orderSubTotal
        orderData.orderTotal = type.orderTotal
        orderData.firestoreUid = type.firestoreUid

        return orderData
    }

    override fun mapToRemote(type: OrderDataPojo): OrderDataPayload {
        val orderData = OrderDataPayload()
        orderData.id = type.id
        orderData.orderRef = type.orderRef
        orderData.status = type.status
        orderData.createdAt = type.createdAt
        orderData.updatedAt = type.updatedAt
        orderData.itemIds = type.itemIds
        orderData.phone = type.phone
        orderData.name = type.name
        orderData.address = type.address
        orderData.lng = type.lng
        orderData.lat = type.lat
        orderData.orderDescription = type.orderDescription

        val orderItems = mutableListOf<OrderItemPayload>()
        type.items!!.map { orderItems.add(mapOrderItemToRemote(it)) }
        orderData.items = orderItems

        orderData.deliveryFee = type.deliveryFee
        orderData.orderItemsQuantity = type.orderItemsQuantity
        orderData.orderSubTotal = type.orderSubTotal
        orderData.orderTotal = type.orderTotal
        orderData.firestoreUid = type.firestoreUid

        return orderData
    }
}
