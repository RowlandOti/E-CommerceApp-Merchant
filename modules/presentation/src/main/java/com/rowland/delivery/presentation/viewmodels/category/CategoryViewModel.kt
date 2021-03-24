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

package com.rowland.delivery.presentation.viewmodels.category

import com.rowland.delivery.domain.models.category.CategoryEntity
import com.rowland.delivery.domain.usecases.category.CreateCategoryUseCase
import com.rowland.delivery.domain.usecases.category.LoadCategoriesUseCase
import com.rowland.delivery.presentation.data.Resource
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.mapper.category.CategoryMapper
import com.rowland.delivery.presentation.model.category.CategoryModel
import com.rowland.delivery.presentation.viewmodels.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

/**
 * Created by Rowland on 5/14/2018.
 */

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val mapper: CategoryMapper
) : SharedViewModel<CategoryModel>() {

    fun loadCategories(userUID: String) {
        this.loadCategoriesUseCase.execute(CategorySubscriber(), LoadCategoriesUseCase.Params.forOrders(userUID))
    }

    fun createCategory(categoryEntity: CategoryEntity, userUID: String): Single<CategoryEntity> {
        return createCategoryUseCase.execute(CreateCategoryUseCase.Params.forCategory(categoryEntity, userUID))
    }

    inner class CategorySubscriber : DisposableSubscriber<List<CategoryEntity>>() {

        override fun onComplete() {
        }

        override fun onNext(t: List<CategoryEntity>) {
            dataList.postValue(Resource(ResourceState.SUCCESS, t.map { mapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            dataList.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}
