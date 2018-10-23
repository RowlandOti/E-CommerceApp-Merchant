package com.rowland.delivery.features.dash.domain.usecases.category

import com.rowland.delivery.features.dash.domain.contracts.ICategoryRepository
import com.rowland.delivery.features.dash.domain.models.category.CategoryEntity
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Rowland on 5/4/2018.
 */

class CreateCategoryUseCase @Inject
constructor(private val categoryRepository: ICategoryRepository) {

    fun createCategory(categoryEntity: CategoryEntity, userUid: String): Single<CategoryEntity> {
        return categoryRepository.createCategory(categoryEntity, userUid)
    }
}
