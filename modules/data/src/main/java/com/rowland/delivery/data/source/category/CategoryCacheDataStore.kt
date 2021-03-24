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

package com.rowland.delivery.data.source.category

import com.rowland.delivery.data.contracts.category.ICategoryCacheSource
import com.rowland.delivery.data.contracts.category.ICategoryDataStore
import com.rowland.delivery.data.model.category.CategoryPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CategoryCacheDataStore @Inject constructor(private val dataSource: ICategoryCacheSource) : ICategoryDataStore {

    override fun saveToCache(categories: List<CategoryPojo>): Completable {
        return dataSource.saveToCache(categories)
    }

    override fun isCached(): Single<Boolean> {
        return dataSource.isCached()
    }

    override fun createCategory(category: CategoryPojo, userUid: String): Single<CategoryPojo> {
        return dataSource.createCategory(category)
    }

    override fun updateCategory(categoryUpdateFields: HashMap<String, Any>, categoryUid: String): Completable {
        return dataSource.updateCategory(categoryUpdateFields, categoryUid)
    }

    override fun deleteCategory(categoryUid: String, userUid: String): Completable {
        return dataSource.deleteCategory(categoryUid, userUid)
    }

    override fun loadCategories(userUid: String): Flowable<List<CategoryPojo>> {
        return dataSource.loadCategories(userUid)
    }

    override fun clearFromCache(): Completable {
        return dataSource.clearFromCache()
    }
}
