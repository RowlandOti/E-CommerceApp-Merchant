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

package com.rowland.delivery.domain.contracts

import com.rowland.delivery.domain.models.category.CategoryEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ICategoryRepository {

    fun clearFromCache(): Completable
    fun saveToCache(categorys: List<CategoryEntity>): Completable
    fun loadCategories(userUid: String): Flowable<List<CategoryEntity>>
    fun createCategory(categoryEntity: CategoryEntity, userUid: String): Single<CategoryEntity>
    fun updateCategory(categoryUpdateFields: HashMap<String, Any>, categoryUid: String): Completable
    fun deleteCategory(categoryUid: String, userUid: String): Completable
}
