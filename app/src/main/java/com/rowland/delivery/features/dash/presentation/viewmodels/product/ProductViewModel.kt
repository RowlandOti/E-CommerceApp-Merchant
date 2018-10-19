package com.rowland.delivery.features.dash.presentation.viewmodels.product

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.features.dash.domain.models.product.Product
import com.rowland.delivery.features.dash.domain.usecases.product.DeleteProductsUseCase
import com.rowland.delivery.features.dash.domain.usecases.product.LoadProductsUseCase
import com.rowland.delivery.features.dash.presentation.viewmodels.SharedViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Rowland on 5/13/2018.
 */

class ProductViewModel @Inject
constructor(private val loadProductsUseCase: LoadProductsUseCase, private val deleteProductsUseCase: DeleteProductsUseCase) : SharedViewModel<Product>() {

    internal val productCategory = MutableLiveData<String>()

    fun setCategory(category: String) {
        productCategory.value = category
        this.loadProductsUseCase.loadProducts(FirebaseAuth.getInstance().currentUser!!.uid, productCategory.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ products -> dataList.setValue(products) }, { throwable -> Log.d(ProductViewModel::class.java.simpleName, "Error Retrieving List: $throwable") }) { Log.d(ProductViewModel::class.java.simpleName, "Done Retrieving") }
    }

    fun deleteProduct(): Completable {
        return this.deleteProductsUseCase.deleteProduct(this.getSelectedListItem().value!!.firestoreUid!!)
    }
}
