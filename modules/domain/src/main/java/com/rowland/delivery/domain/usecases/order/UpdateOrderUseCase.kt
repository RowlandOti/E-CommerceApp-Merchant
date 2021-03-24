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
import com.rowland.delivery.domain.usecases.CompletableUseCase
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject

class UpdateOrderUseCase @Inject
constructor(
    private val orderRepository: IOrderRepository,
    threadExecutor: IThreadExecutor,
    postExecutionThread: IPostExecutionThread
) : CompletableUseCase<UpdateOrderUseCase.Params>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Params): Completable {
        return this.orderRepository.updateOrder(params.orderUpdateFields, params.orderUID)
    }

    fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUID: String): Completable {
        return this.orderRepository.updateOrder(orderUpdateFields, orderUID)
    }

    data class Params constructor(val orderUpdateFields: HashMap<String, Any>, val orderUID: String) {
        companion object {
            fun forOrder(orderUpdateFields: HashMap<String, Any>, orderUID: String): Params {
                return Params(orderUpdateFields, orderUID)
            }
        }
    }
}
