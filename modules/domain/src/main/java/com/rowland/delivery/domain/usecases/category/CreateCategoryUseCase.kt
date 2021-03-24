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

package com.rowland.delivery.domain.usecases.category

import com.rowland.delivery.domain.contracts.ICategoryRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.models.category.CategoryEntity
import com.rowland.delivery.domain.usecases.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Rowland on 5/4/2018.
 */

class CreateCategoryUseCase @Inject
constructor(
    private val categoryRepository: ICategoryRepository,
    threadExecutor: IThreadExecutor,
    postExecutionThread: IPostExecutionThread
) : SingleUseCase<CategoryEntity, CreateCategoryUseCase.Params>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: CreateCategoryUseCase.Params?): Single<CategoryEntity> {
        return categoryRepository.createCategory(params!!.categoryEntity, params!!.userUID)
    }

    fun createCategory(categoryEntity: CategoryEntity, userUid: String): Single<CategoryEntity> {
        return categoryRepository.createCategory(categoryEntity, userUid)
    }

    data class Params constructor(val categoryEntity: CategoryEntity, val userUID: String) {
        companion object {
            fun forCategory(categoryEntity: CategoryEntity, userUiD: String): Params {
                return Params(categoryEntity, userUiD)
            }
        }
    }
}
