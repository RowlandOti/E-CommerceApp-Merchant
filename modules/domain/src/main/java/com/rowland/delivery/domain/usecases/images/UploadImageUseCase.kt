package com.rowland.delivery.domain.usecases.images


import com.rowland.delivery.domain.contracts.IImageRepository
import io.reactivex.Flowable
import javax.inject.Inject

class UploadImageUseCase @Inject
constructor(private val imageRepository: IImageRepository) {

    fun uploadImages(imageEntitys: List<String>, imageCategory: String, userUid: String): Flowable<List<String>> {
        return this.imageRepository.uploadImages(imageEntitys, userUid, imageCategory)
    }
}