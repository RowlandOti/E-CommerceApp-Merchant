package com.rowland.delivery.presentation.viewmodels.product

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rowland.delivery.domain.models.product.ProductEntity
import com.rowland.delivery.domain.usecases.images.UploadImageUseCase
import com.rowland.delivery.domain.usecases.product.CreateProductUseCase
import io.reactivex.Flowable
import javax.inject.Inject


/**
 * Created by Rowland on 5/15/2018.
 */

class NewProductViewModel @Inject constructor(private val createProductUseCase: CreateProductUseCase, private val uploadImageUseCase: UploadImageUseCase) : ViewModel() {

    internal val selectedImageUri = MutableLiveData<List<String>>()

    val images: LiveData<List<String>>
        get() = selectedImageUri

    fun setImageUri(imageUris: List<Uri>) {
        val imageStrings = ArrayList<String>()
        for (uri in imageUris) {
            imageStrings.add(uri.toString())
        }
        selectedImageUri.value = imageStrings
    }

    fun createProduct(productEntity: ProductEntity,category: String,userUid: String): Flowable<ProductEntity> {
        return uploadImageUseCase.uploadImages(selectedImageUri.value!!, category, userUid)
                .map {
                    productEntity.imageUrls = it as ArrayList<String>
                    productEntity.merchantCode = userUid
                    productEntity
                }
                .flatMapSingle { createProductUseCase.execute(CreateProductUseCase.Params.forProducts(it, category, userUid)) }
    }
}
