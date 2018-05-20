package com.rowland.delivery.features.dash.domain.usecases.category

import com.rowland.delivery.features.dash.data.model.category.CategoryRepository
import com.rowland.delivery.features.dash.domain.models.category.Category
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Rowland on 5/4/2018.
 */

class CreateCategoryUseCase @Inject
constructor(private val categoryRepository: CategoryRepository) {

    fun createCategory(category: Category, userUid: String): Single<Category> {
        return categoryRepository.createCategory(category, userUid)
    }
}
