package com.rowland.delivery.data.source.image

import com.rowland.delivery.data.contracts.image.IImageDataStore
import com.rowland.delivery.data.contracts.image.IImageRemoteSource
import io.reactivex.Flowable
import javax.inject.Inject

class ImageRemoteDataStore  @Inject constructor(private val dataSource: IImageRemoteSource) : IImageDataStore {

    override fun uploadImages(imageEntitys: List<String>, imageCategory: String, userUid: String): Flowable<List<String>> {
        return dataSource.uploadImages(imageEntitys, imageCategory, userUid)
    }
}