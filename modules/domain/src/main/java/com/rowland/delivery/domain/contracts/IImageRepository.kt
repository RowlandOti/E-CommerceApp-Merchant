package com.rowland.delivery.domain.contracts

import io.reactivex.Flowable

interface IImageRepository {
    fun uploadImages(imageEntitys: List<String>, imageCategory: String, userUid: String): Flowable<List<String>>
}