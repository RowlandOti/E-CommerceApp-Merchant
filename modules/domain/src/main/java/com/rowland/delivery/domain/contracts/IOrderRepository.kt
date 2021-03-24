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

package com.rowland.delivery.domain.contracts

import com.rowland.delivery.domain.models.order.OrderDataEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IOrderRepository {
    fun clearFromCache(): Completable
    fun saveToCache(orders: List<OrderDataEntity>): Completable
    fun loadOrders(userUid: String): Flowable<List<OrderDataEntity>>
    fun createOrder(orderDataEntity: OrderDataEntity, userUid: String): Single<OrderDataEntity>
    fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable
    fun deleteOrder(orderUid: String): Completable
}
