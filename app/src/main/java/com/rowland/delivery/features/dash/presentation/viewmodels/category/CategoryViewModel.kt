package com.rowland.delivery.features.dash.presentation.viewmodels.category

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.features.dash.domain.models.category.CategoryEntity
import com.rowland.delivery.features.dash.domain.usecases.category.CreateCategoryUseCase
import com.rowland.delivery.features.dash.domain.usecases.category.LoadCategoriesUseCase
import com.rowland.delivery.features.dash.presentation.viewmodels.SharedViewModel
import com.rowland.delivery.features.dash.presentation.viewmodels.ViewEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Rowland on 5/14/2018.
 */

class CategoryViewModel @Inject constructor(private val loadCategoriesUseCase: LoadCategoriesUseCase, private val createCategoryUseCase: CreateCategoryUseCase) : SharedViewModel<CategoryEntity>() {

    init {
        loadCategoriesUseCase.loadCategories(FirebaseAuth.getInstance().currentUser!!.uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categories ->
                    dataList.value = categories
                    updateState { it.copy(event = ViewEvent.Success()) }
                }, { throwable ->
                    updateState { it.copy(event = ViewEvent.Error()) }
                }) {
                    Log.d(CategoryViewModel::class.java.simpleName, "Done Retrieving")
                }
    }


    fun saveCategory(categoryEntity: CategoryEntity): Single<CategoryEntity> {
        return createCategoryUseCase.createCategory(categoryEntity, FirebaseAuth.getInstance().currentUser!!.uid)
    }
}
