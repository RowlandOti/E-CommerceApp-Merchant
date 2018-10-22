package com.rowland.delivery.features.dash.domain.contracts

import com.rowland.delivery.features.dash.domain.models.category.Category
import io.reactivex.Flowable
import io.reactivex.Single

interface ICategoryRepository {
    fun getCategories(userUid: String): Flowable<List<Category>>
    fun createCategory(category: Category, userUid: String): Single<Category>
}