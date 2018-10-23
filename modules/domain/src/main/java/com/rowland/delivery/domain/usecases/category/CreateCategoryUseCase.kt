package com.rowland.delivery.domain.usecases.category

import com.rowland.delivery.domain.contracts.ICategoryRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.models.category.CategoryEntity
import com.rowland.delivery.domain.usecases.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Rowland on 5/4/2018.
 */

class CreateCategoryUseCase @Inject
constructor(private val categoryRepository: ICategoryRepository, threadExecutor: IThreadExecutor, postExecutionThread: IPostExecutionThread) : SingleUseCase<CategoryEntity, CreateCategoryUseCase.Params>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: CreateCategoryUseCase.Params?): Single<CategoryEntity> {
        return categoryRepository.createCategory(params!!.categoryEntity, params!!.userUID)
    }

    fun createCategory(categoryEntity: CategoryEntity, userUid: String): Single<CategoryEntity> {
        return categoryRepository.createCategory(categoryEntity, userUid)
    }

    data class Params constructor(val categoryEntity: CategoryEntity, val userUID: String) {
        companion object {
            fun forCategory(categoryEntity: CategoryEntity, userUiD: String): Params {
                return Params(categoryEntity, userUiD)
            }
        }
    }
}
