package com.rowland.delivery.data.contracts.image

import io.reactivex.Flowable

interface IImageRemoteSource {
    fun uploadImages(imageEntitys: List<String>, imageCategory: String, userUid: String): Flowable<List<String>>
}