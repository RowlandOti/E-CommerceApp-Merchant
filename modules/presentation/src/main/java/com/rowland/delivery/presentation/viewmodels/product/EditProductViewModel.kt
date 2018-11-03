package com.rowland.delivery.presentation.viewmodels.product

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rowland.delivery.domain.usecases.images.UploadImageUseCase
import com.rowland.delivery.domain.usecases.product.UpdateProductUseCase
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject

class EditProductViewModel @Inject constructor(private val updateProductUseCase: UpdateProductUseCase, private val uploadImageUseCase: UploadImageUseCase) : ViewModel() {

    internal val productUid = MutableLiveData<String>()
    internal val isImageSelected = MutableLiveData<Boolean>()
    internal val currentUserUid = MutableLiveData<String>()
    internal val productCategory = MutableLiveData<String>()
    internal val selectedImageUri = MutableLiveData<List<String>>()

    val images: LiveData<List<String>>
        get() = selectedImageUri


    fun setImagesIsSelected(isSelected: Boolean) {
        isImageSelected.value = isSelected
    }

    fun setCategory(category: String) {
        productCategory.value = category
    }

    fun setProductUid(firebaseUid: String) {
        productUid.value = firebaseUid
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

    fun updateProduct(productUpdateFields: HashMap<String, Any>): Completable {
        if (isImageSelected.value == true) {
            return uploadImageUseCase.uploadImages(selectedImageUri.value!!, productCategory.value!!, currentUserUid.value!!)
                    .map { updateProduct(productUpdateFields, it) }
                    .switchMapCompletable { updateProductUseCase.updateProduct(it, productUid.value!!) }
        } else {
            return updateProductUseCase.updateProduct(productUpdateFields, productUid.value!!)
        }
    }

    private fun updateProduct(productUpdateFields: HashMap<String, Any>, imageUrls: List<String>): HashMap<String, Any> {
        productUpdateFields.put("imageUrls", imageUrls)
        return productUpdateFields
    }
}