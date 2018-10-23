package com.rowland.delivery.features.dash.presentation.viewmodels.product

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.features.dash.domain.models.product.ProductEntity
import com.rowland.delivery.features.dash.domain.usecases.product.DeleteProductsUseCase
import com.rowland.delivery.features.dash.domain.usecases.product.LoadProductsUseCase
import com.rowland.delivery.features.dash.presentation.data.Resource
import com.rowland.delivery.features.dash.presentation.data.ResourceState
import com.rowland.delivery.features.dash.presentation.mapper.product.ProductMapper
import com.rowland.delivery.features.dash.presentation.model.ProductModel
import com.rowland.delivery.features.dash.presentation.viewmodels.SharedViewModel
import io.reactivex.Completable
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

class ProductViewModel @Inject
constructor(private val loadProductsUseCase: LoadProductsUseCase, private val deleteProductsUseCase: DeleteProductsUseCase, private val productMapper: ProductMapper) : SharedViewModel<ProductModel>() {

    internal val productCategory = MutableLiveData<String>()

    fun setCategory(category: String) {
        productCategory.value = category
        this.loadProductsUseCase.execute(ProductsSubscriber(), LoadProductsUseCase.Params.forProducts(FirebaseAuth.getInstance().currentUser!!.uid, productCategory.value!!))
    }

    fun deleteProduct(): Completable {
        return this.deleteProductsUseCase.deleteProduct(this.getSelectedListItem().value!!.firestoreUid!!)
    }

    inner class ProductsSubscriber : DisposableSubscriber<List<ProductEntity>>() {
        override fun onComplete() {

        }

        override fun onNext(t: List<ProductEntity>) {
            dataList.postValue(Resource(ResourceState.SUCCESS, t.map { productMapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            dataList.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}
