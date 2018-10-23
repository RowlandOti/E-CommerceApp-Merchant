package com.rowland.delivery.features.dash.presentation.viewmodels.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.rowland.delivery.features.dash.domain.models.product.ProductEntity
import com.rowland.delivery.features.dash.domain.usecases.product.UpdateProductUseCase
import com.rowland.rxgallery.RxGallery
import durdinapps.rxfirebase2.RxFirebaseStorage
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class EditProductViewModel @Inject constructor(private val context: Context, private val updateProductUseCase: UpdateProductUseCase) : ViewModel() {

    private val productFirebaseUid = MutableLiveData<String>()
    private val productCategory = MutableLiveData<String>()
    private val selectedImageUri = MutableLiveData<List<Uri>>()
    private val isImageSelected = MutableLiveData<Boolean>()

    val images: LiveData<List<Uri>>
        get() = selectedImageUri

    fun setCategory(category: String) {
        productCategory.value = category
    }

    fun setProductFirebaseUid(firebaseUid: String) {
        productFirebaseUid.value = firebaseUid
    }

    fun selectImages() {
        RxGallery.gallery(context, true, RxGallery.MimeType.IMAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ uris ->
                    selectedImageUri.setValue(uris)
                    isImageSelected.value = true
                }, { throwable -> Log.d(EditProductViewModel::class.java.simpleName, "Error Selecting Images: $throwable") }) {
                    Log.d(EditProductViewModel::class.java.simpleName, "Done Selecting Images")
                }
    }

    fun getPhotoUrl(uri: Uri): Single<String>? {
        val photoRef = FirebaseStorage.getInstance()
                .reference
                .child("photos").child("products").child(productCategory.value!!).child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(uri.lastPathSegment!!)

        return RxFirebaseStorage.putFile(photoRef, uri)
                .map { taskSnapshot -> taskSnapshot.metadata!!.path }
    }


    fun updateProduct(productUpdateFields: HashMap<String, Any>): Single<ProductEntity> {
        if (isImageSelected.value == true) {
            return Observable.fromArray(selectedImageUri.value!!)
                    .flatMapIterable { uris -> uris }
                    .flatMap { uri -> getPhotoUrl(uri)!!.subscribeOn(Schedulers.io()).toObservable() }
                    .toList()
                    .map { t ->
                        productUpdateFields.put("imageUrls", t)
                        isImageSelected.value = false
                    }
                    .flatMap { t ->
                        updateProductUseCase.updateProduct(productUpdateFields, productFirebaseUid.value!!)
                    }
        } else {
            return updateProductUseCase.updateProduct(productUpdateFields, productFirebaseUid.value!!)
        }
    }
}