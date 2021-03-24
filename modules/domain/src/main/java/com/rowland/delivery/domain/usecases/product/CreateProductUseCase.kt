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
import com.rowland.delivery.domain.models.product.ProductEntity
import com.rowland.delivery.domain.usecases.SingleUseCase

import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

class CreateProductUseCase @Inject
constructor(
    private val productRepository: IProductRepository,
    threadExecutor: IThreadExecutor,
    postExecutionThread: IPostExecutionThread
) : SingleUseCase<ProductEntity, CreateProductUseCase.Params>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Single<ProductEntity> {
        return this.productRepository.createProduct(params!!.productEntity, params.productCategory, params!!.userUiD)
    }

    data class Params constructor(val productEntity: ProductEntity, val productCategory: String, val userUiD: String) {
        companion object {
            fun forProducts(productEntity: ProductEntity, productCategory: String, userUiD: String): Params {
                return Params(productEntity, productCategory, userUiD)
            }
        }
    }
}
