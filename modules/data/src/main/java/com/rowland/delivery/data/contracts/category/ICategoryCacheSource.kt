package com.rowland.delivery.data.contracts.category

import com.rowland.delivery.data.model.category.CategoryPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface ICategoryCacheSource {
    fun createCategory(Categories: CategoryPojo): Single<CategoryPojo>
    fun updateCategory(categoryUpdateFields: HashMap<String, Any>, categoryUid: String): Completable
    fun deleteCategory(categoryUid: String, userUid: String): Completable
    fun loadCategories(userUid: String): Flowable<List<CategoryPojo>>
    fun saveToCache(categories: List<CategoryPojo>): Completable
    fun clearFromCache(): Completable
    fun isCached(): Single<Boolean>
    fun setLastCacheTime(lastCache: Long)
    fun isExpired(): Boolean
}