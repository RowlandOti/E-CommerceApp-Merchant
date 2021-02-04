package com.rowland.delivery.presentation.viewmodels.category

import com.rowland.delivery.domain.models.category.CategoryEntity
import com.rowland.delivery.domain.usecases.category.CreateCategoryUseCase
import com.rowland.delivery.domain.usecases.category.LoadCategoriesUseCase
import com.rowland.delivery.presentation.data.Resource
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.mapper.category.CategoryMapper
import com.rowland.delivery.presentation.model.category.CategoryModel
import com.rowland.delivery.presentation.viewmodels.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

/**
 * Created by Rowland on 5/14/2018.
 */

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val mapper: CategoryMapper
) : SharedViewModel<CategoryModel>() {

    fun loadCategories(userUID: String) {
        this.loadCategoriesUseCase.execute(CategorySubscriber(), LoadCategoriesUseCase.Params.forOrders(userUID))
    }

    fun createCategory(categoryEntity: CategoryEntity, userUID: String): Single<CategoryEntity> {
        return createCategoryUseCase.execute(CreateCategoryUseCase.Params.forCategory(categoryEntity, userUID))
    }

    inner class CategorySubscriber : DisposableSubscriber<List<CategoryEntity>>() {

        override fun onComplete() {
        }

        override fun onNext(t: List<CategoryEntity>) {
            dataList.postValue(Resource(ResourceState.SUCCESS, t.map { mapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            dataList.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}
