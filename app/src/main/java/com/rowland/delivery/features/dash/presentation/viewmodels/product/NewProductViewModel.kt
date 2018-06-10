package com.rowland.delivery.features.dash.presentation.viewmodels.product

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.rowland.delivery.features.dash.domain.models.product.Product
import com.rowland.delivery.features.dash.domain.usecases.product.CreateProductUseCase
import com.rowland.rxgallery.RxGallery
import durdinapps.rxfirebase2.RxFirebaseStorage
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by Rowland on 5/15/2018.
 */

class NewProductViewModel @Inject constructor(private val context: Context, private val createProductUseCase: CreateProductUseCase) : ViewModel() {

    private val productCategory = MutableLiveData<String>()
    private val selectedImageUri = MutableLiveData<List<Uri>>()

    val images: LiveData<List<Uri>>
        get() = selectedImageUri

    fun setCategory(category: String) {
        productCategory.value = category
    }

    fun selectImages() {
        RxGallery.gallery(context, true, RxGallery.MimeType.IMAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ uris -> selectedImageUri.setValue(uris) }, { throwable -> Log.d(NewProductViewModel::class.java.simpleName, "Error Selecting Images: $throwable") }) { Log.d(NewProductViewModel::class.java.simpleName, "Done Selecting Images") }
    }

    fun getPhotoUrl(uri: Uri): Single<String>? {
        val photoRef = FirebaseStorage.getInstance()
                .reference
                .child("photos").child("products").child(productCategory.value!!).child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(uri.lastPathSegment)

        return RxFirebaseStorage.putFile(photoRef, uri)
                .map { taskSnapshot -> taskSnapshot.metadata!!.path }
    }


    fun saveProduct(product: Product): Single<Product> {
        return Observable.fromArray(selectedImageUri.value!!)
                .flatMapIterable({ uris -> uris })
                .flatMap({ uri -> getPhotoUrl(uri)!!.subscribeOn(Schedulers.io()).toObservable() })
                .toList()
                .map { t ->
                    product.merchantCode = FirebaseAuth.getInstance().currentUser!!.uid
                    product.imageUrls = t as ArrayList<String>
                    product
                }
                .flatMap { t -> createProductUseCase.createProduct(product, FirebaseAuth.getInstance().currentUser!!.uid, productCategory.value!!) }

    }
}
