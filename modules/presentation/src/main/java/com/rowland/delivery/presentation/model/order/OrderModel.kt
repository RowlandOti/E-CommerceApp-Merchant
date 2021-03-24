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

package com.rowland.delivery.presentation.model.order

import java.util.*

/**
 * Created by Rowland on 5/8/2018.
 */

class OrderModel {

    val id: Int? = null
    val orderRef: String? = null
    val status: String? = null
    val createdAt: Date? = null
    val updatedAt: Date? = null
    val itemIds: String? = null
    val phone: String? = null
    val name: String? = null
    val address: String? = null
    val lng: String? = null
    val lat: String? = null
    val orderDescription: String? = null
    val items: List<OrderItemModel>? = null
    val deliveryFee: Int? = null
    val orderItemsQuantity: Int? = null
    val orderSubTotal: String? = null
    val orderTotal: Int? = null

    override fun toString(): String {
        return "OrderPojo(id=$id, orderRef=$orderRef, status=$status, createdAt=$createdAt, updatedAt=$updatedAt, " +
            "itemIds=$itemIds, phone=$phone, name=$name, address=$address, lng=$lng, lat=$lat, " +
            "orderDescription=$orderDescription, items=$items, deliveryFee=$deliveryFee, " +
            "orderItemsQuantity=$orderItemsQuantity, orderSubTotal=$orderSubTotal, orderTotal=$orderTotal)"
    }
}
