package com.rowland.delivery.domain.usecases.category

import com.rowland.delivery.domain.contracts.ICategoryRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.models.category.CategoryEntity
import com.rowland.delivery.domain.usecases.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Rowland on 5/4/2018.
 */

class LoadCategoriesUseCase @Inject
constructor(private val categoryRepository: ICategoryRepository, threadExecutor: IThreadExecutor, postExecutionThread: IPostExecutionThread) : FlowableUseCase<List<CategoryEntity>, LoadCategoriesUseCase.Params>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: LoadCategoriesUseCase.Params?): Flowable<List<CategoryEntity>> {
        return this.categoryRepository.loadCategories(params!!.userUID)
    }

    fun loadCategories(userUid: String): Flowable<List<CategoryEntity>> {
        return categoryRepository.loadCategories(userUid)
    }

    data class Params constructor(val userUID: String) {
        companion object {
            fun forOrders(userUID: String): Params {
                return Params(userUID)
            }
        }
    }
}
