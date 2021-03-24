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

package com.rowland.delivery.domain.usecases.product

import com.rowland.delivery.domain.contracts.IProductRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.usecases.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class DeleteProductUseCase @Inject
constructor(
    private val productRepository: IProductRepository,
    threadExecutor: IThreadExecutor,
    postExecutionThread: IPostExecutionThread
) : CompletableUseCase<DeleteProductUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Completable {
        return this.productRepository.deleteProduct(params.productUID)
    }

    fun deleteProduct(params: Params): Completable {
        return this.productRepository.deleteProduct(params.productUID)
    }

    data class Params constructor(val productUID: String) {
        companion object {
            fun forProduct(productUID: String): Params {
                return Params(productUID)
            }
        }
    }
}
