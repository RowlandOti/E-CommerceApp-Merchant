package com.rowland.delivery.data.source.image

import com.rowland.delivery.data.contracts.image.IImageDataStore
import javax.inject.Inject

class ImageDataStoreFactory @Inject constructor(private val imageRemoteDataStore: ImageRemoteDataStore) {

    fun retrieveDataStore(): IImageDataStore {
        return imageRemoteDataStore
    }
}