package com.rowland.delivery.presentation.viewmodels.product

import androidx.lifecycle.MutableLiveData
import com.rowland.delivery.domain.models.product.ProductEntity
import com.rowland.delivery.domain.usecases.product.DeleteProductUseCase
import com.rowland.delivery.domain.usecases.product.LoadProductsUseCase
import com.rowland.delivery.presentation.data.Resource
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.mapper.product.ProductMapper
import com.rowland.delivery.presentation.model.product.ProductModel
import com.rowland.delivery.presentation.viewmodels.SharedViewModel
import io.reactivex.Completable
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

class ProductViewModel @Inject
constructor(private val loadProductsUseCase: LoadProductsUseCase, private val deleteProductsUseCase: DeleteProductUseCase, private val mapper: ProductMapper) : SharedViewModel<ProductModel>() {

    internal val productCategory = MutableLiveData<String>()
    internal val currentUserUid = MutableLiveData<String>()

    fun loadProducts(category: String, userUid: String) {
        currentUserUid.value = userUid
        productCategory.value = category
        this.loadProductsUseCase.execute(ProductsSubscriber(), LoadProductsUseCase.Params.forProducts(currentUserUid.value!!, productCategory.value!!))
    }

    fun deleteProduct(): Completable {
        return this.deleteProductsUseCase.execute(DeleteProductUseCase.Params.forProduct(this.getSelectedListItem().value!!.firestoreUid!!))
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
