package com.rowland.delivery.data.repository.image


import com.rowland.delivery.data.source.image.ImageDataStoreFactory
import com.rowland.delivery.domain.contracts.IImageRepository
import io.reactivex.Flowable
import javax.inject.Inject

class ImageRepository @Inject constructor(private val dataStoreFactory: ImageDataStoreFactory) : IImageRepository {

    override fun uploadImages(imageEntitys: List<String>, imageCategory: String, userUid: String): Flowable<List<String>> {
        return dataStoreFactory.retrieveDataStore().uploadImages(imageEntitys, imageCategory, userUid)


    }
}