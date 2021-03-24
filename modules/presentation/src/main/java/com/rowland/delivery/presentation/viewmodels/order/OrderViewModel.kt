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

package com.rowland.delivery.presentation.viewmodels.order

import com.rowland.delivery.domain.models.order.OrderDataEntity
import com.rowland.delivery.domain.usecases.order.LoadOrdersUseCase
import com.rowland.delivery.domain.usecases.order.UpdateOrderUseCase
import com.rowland.delivery.presentation.data.Resource
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.mapper.order.OrderDataMapper
import com.rowland.delivery.presentation.model.order.OrderDataModel
import com.rowland.delivery.presentation.viewmodels.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.subscribers.DisposableSubscriber
import java.util.HashMap
import javax.inject.Inject

/**
 * Created by Rowland on 5/8/2018.
 */

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val loadOrdersUseCase: LoadOrdersUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val mapper: OrderDataMapper,
) : SharedViewModel<OrderDataModel>() {

    fun loadOrders(userUID: String) {
        this.loadOrdersUseCase.execute(OrderDataSubscriber(), LoadOrdersUseCase.Params.forOrders(userUID))
    }

    fun updateOrder(orderUpdateFields: HashMap<String, Any>, firestoreUid: String): Completable {
        return updateOrderUseCase.updateOrder(orderUpdateFields, firestoreUid)
    }

    inner class OrderDataSubscriber : DisposableSubscriber<List<OrderDataEntity>>() {

        override fun onComplete() {
        }

        override fun onNext(t: List<OrderDataEntity>) {
            dataList.postValue(Resource(ResourceState.SUCCESS, t.map { mapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            dataList.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}
