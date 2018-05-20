package com.rowland.delivery.features.dash.domain.usecases.category

import com.rowland.delivery.features.dash.data.model.category.CategoryRepository
import com.rowland.delivery.features.dash.domain.models.category.Category
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Rowland on 5/4/2018.
 */

class LoadCategoriesUseCase @Inject
constructor(private val categoryRepository: CategoryRepository) {

    fun loadCategories(userUid: String): Flowable<List<Category>> {
        return categoryRepository.getCategories(userUid)
    }
}
