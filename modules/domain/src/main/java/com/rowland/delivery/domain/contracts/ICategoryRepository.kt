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