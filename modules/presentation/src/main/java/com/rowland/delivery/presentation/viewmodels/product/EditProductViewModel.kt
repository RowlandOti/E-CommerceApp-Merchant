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

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rowland.delivery.domain.usecases.images.UploadImageUseCase
import com.rowland.delivery.domain.usecases.product.UpdateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val updateProductUseCase: UpdateProductUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

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
            return uploadImageUseCase.uploadImages(
                selectedImageUri.value!!,
                productCategory.value!!,
                currentUserUid.value!!
            )
                .map { updateProduct(productUpdateFields, it) }
                .switchMapCompletable { updateProductUseCase.updateProduct(it, productUid.value!!) }
        } else {
            return updateProductUseCase.updateProduct(productUpdateFields, productUid.value!!)
        }
    }

    private fun updateProduct(
        productUpdateFields: HashMap<String, Any>,
        imageUrls: List<String>
    ): HashMap<String, Any> {
        productUpdateFields.put("imageUrls", imageUrls)
        return productUpdateFields
    }
}
