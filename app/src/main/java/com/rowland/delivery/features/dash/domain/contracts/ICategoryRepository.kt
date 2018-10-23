package com.rowland.delivery.features.dash.domain.contracts

import com.rowland.delivery.features.dash.domain.models.category.CategoryEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface ICategoryRepository {
    fun getCategories(userUid: String): Flowable<List<CategoryEntity>>
    fun createCategory(categoryEntity: CategoryEntity, userUid: String): Single<CategoryEntity>
}