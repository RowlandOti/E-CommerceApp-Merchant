package com.rowland.delivery.presentation.viewmodels.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.net.Uri
import com.rowland.delivery.domain.models.product.ProductEntity
import com.rowland.delivery.domain.usecases.images.UploadImageUseCase
import com.rowland.delivery.domain.usecases.product.CreateProductUseCase
import io.reactivex.Flowable
import javax.inject.Inject


/**
 * Created by Rowland on 5/15/2018.
 */

class NewProductViewModel @Inject constructor(private val createProductUseCase: CreateProductUseCase, private val uploadImageUseCase: UploadImageUseCase) : ViewModel() {

    internal val currentUserUid = MutableLiveData<String>()
    internal val productCategory = MutableLiveData<String>()
    internal val selectedImageUri = MutableLiveData<List<String>>()

    val images: LiveData<List<String>>
        get() = selectedImageUri

    fun setCategory(category: String) {
        productCategory.value = category
    }

    fun seUserUid(userUid: String) {
       currentUserUid.value = userUid
    }

    fun setImageUri(imageUris: List<Uri>) {
        val imageStrings = ArrayList<String>()
        for (uri in imageUris) {
            imageStrings.add(uri.toString())
        }
        selectedImageUri.value = imageStrings
    }

    fun createProduct(productEntity: ProductEntity): Flowable<ProductEntity> {
        return uploadImageUseCase.uploadImages(null!!, productCategory.value!!, currentUserUid.value!!)
                .map { updateProduct(productEntity, it) }
                .flatMapSingle { createProductUseCase.execute(CreateProductUseCase.Params.forProducts(it, currentUserUid.value!!, productCategory.value!!)) }
    }

    private fun updateProduct(productEntity: ProductEntity, imageUrls: List<String>): ProductEntity {
        productEntity.imageUrls = imageUrls as ArrayList<String>
        productEntity.merchantCode = ""
        return productEntity
    }
}
