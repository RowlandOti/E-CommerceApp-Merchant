package com.rowland.delivery.data.contracts.category

import com.rowland.delivery.data.model.category.CategoryPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface ICategoryDataStore {
    fun clearFromCache(): Completable
    fun saveToCache(categories: List<CategoryPojo>): Completable
    fun createCategory(category: CategoryPojo, userUid: String): Single<CategoryPojo>
    fun updateCategory(CategoryUpdateFields: HashMap<String, Any>, CategoryUid: String): Completable
    fun deleteCategory(CategoryUid: String, userUid: String): Completable
    fun loadCategories(userUid: String): Flowable<List<CategoryPojo>>
    fun isCached(): Single<Boolean>
}