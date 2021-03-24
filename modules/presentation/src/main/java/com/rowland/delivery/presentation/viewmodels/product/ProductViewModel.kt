/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.presentation.viewmodels.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rowland.delivery.domain.models.product.ProductEntity
import com.rowland.delivery.domain.usecases.product.DeleteProductUseCase
import com.rowland.delivery.domain.usecases.product.LoadProductsUseCase
import com.rowland.delivery.presentation.data.Resource
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.mapper.product.ProductMapper
import com.rowland.delivery.presentation.model.product.ProductModel
import com.rowland.delivery.presentation.viewmodels.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

@HiltViewModel
class ProductViewModel
@Inject constructor(
    private val loadProductsUseCase: LoadProductsUseCase,
    private val deleteProductsUseCase: DeleteProductUseCase,
    private val mapper: ProductMapper
) : SharedViewModel<ProductModel>() {

    internal val productCategory = MutableLiveData<String>()
    internal val currentUserUid = MutableLiveData<String>()

    val category: LiveData<String>
        get() = productCategory

    fun loadProducts(category: String, userUid: String) {
        currentUserUid.value = userUid
        productCategory.value = category
        this.loadProductsUseCase.execute(
            ProductsSubscriber(),
            LoadProductsUseCase.Params.forProducts(currentUserUid.value!!, productCategory.value!!)
        )
    }

    fun deleteProduct(): Completable {
        return this.deleteProductsUseCase.deleteProduct(
            DeleteProductUseCase.Params.forProduct(
                this.getSelectedListItem().value!!.firestoreUid!!
            )
        )
    }

    inner class ProductsSubscriber : DisposableSubscriber<List<ProductEntity>>() {

        override fun onComplete() {
        }

        override fun onNext(t: List<ProductEntity>) {
            dataList.postValue(Resource(ResourceState.SUCCESS, t.map { mapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            dataList.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}
