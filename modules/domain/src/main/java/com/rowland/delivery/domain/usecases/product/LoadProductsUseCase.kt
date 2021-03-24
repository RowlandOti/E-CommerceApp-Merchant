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

package com.rowland.delivery.domain.usecases.product

import com.rowland.delivery.domain.contracts.IProductRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.models.product.ProductEntity
import com.rowland.delivery.domain.usecases.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

class LoadProductsUseCase @Inject
constructor(
    private val productRepository: IProductRepository,
    threadExecutor: IThreadExecutor,
    postExecutionThread: IPostExecutionThread
) : FlowableUseCase<List<ProductEntity>, LoadProductsUseCase.Params>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Params?): Flowable<List<ProductEntity>> {
        return this.productRepository.loadProducts(params!!.userUID, params.productCategory)
    }

    data class Params constructor(val userUID: String, val productCategory: String) {
        companion object {
            fun forProducts(userUID: String, productCategory: String): Params {
                return Params(userUID, productCategory)
            }
        }
    }
}
