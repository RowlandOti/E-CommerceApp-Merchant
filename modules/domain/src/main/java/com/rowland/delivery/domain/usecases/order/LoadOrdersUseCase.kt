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

package com.rowland.delivery.domain.usecases.order

import com.rowland.delivery.domain.contracts.IOrderRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.models.order.OrderDataEntity
import com.rowland.delivery.domain.usecases.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Rowland on 5/8/2018.
 */

class LoadOrdersUseCase @Inject
constructor(
    private val orderRepository: IOrderRepository,
    threadExecutor: IThreadExecutor,
    postExecutionThread: IPostExecutionThread
) : FlowableUseCase<List<OrderDataEntity>, LoadOrdersUseCase.Params>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Flowable<List<OrderDataEntity>> {
        return this.orderRepository.loadOrders(params!!.userUID)
    }

    data class Params constructor(val userUID: String) {
        companion object {
            fun forOrders(userUID: String): Params {
                return Params(userUID)
            }
        }
    }
}
