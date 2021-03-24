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

package com.rowland.delivery.data.source.order

import com.rowland.delivery.data.contracts.order.IOrderCacheSource
import com.rowland.delivery.data.contracts.order.IOrderDataStore
import com.rowland.delivery.data.model.order.OrderDataPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.HashMap
import javax.inject.Inject

class OrderCacheDataStore @Inject constructor(private val dataSource: IOrderCacheSource) : IOrderDataStore {

    override fun saveToCache(orders: List<OrderDataPojo>): Completable {
        return dataSource.saveToCache(orders)
    }

    override fun isCached(): Single<Boolean> {
        return dataSource.isCached()
    }

    override fun createOrder(order: OrderDataPojo, userUid: String): Single<OrderDataPojo> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable {
        return dataSource.updateOrder(orderUpdateFields, orderUid)
    }

    override fun deleteOrder(orderUid: String): Completable {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun loadOrders(userUid: String): Flowable<List<OrderDataPojo>> {
        return dataSource.loadOrders(userUid)
    }

    override fun clearFromCache(): Completable {
        return dataSource.clearFromCache()
    }
}
