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
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject

/**
 * Created by Rowland on 10/19/2018.
 */

class UpdateProductUseCase @Inject
constructor(private val productRepository: IProductRepository) {

    fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable {
        return this.productRepository.updateProduct(productUpdateFields, productUid)
    }
}
