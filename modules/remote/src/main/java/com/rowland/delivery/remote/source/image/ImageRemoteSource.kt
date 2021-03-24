/*
 * Copyright 2021 Otieno Rowland
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

package com.rowland.delivery.remote.source.image

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.rowland.delivery.data.contracts.image.IImageRemoteSource
import durdinapps.rxfirebase2.RxFirebaseStorage
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImageRemoteSource @Inject constructor(private val mFirebaseStorage: FirebaseStorage) : IImageRemoteSource {

    override fun uploadImages(
        imageEntitys: List<String>,
        imageCategory: String,
        userUid: String
    ): Flowable<List<String>> {
        return Observable.fromArray(imageEntitys)
            .flatMapIterable { imageStrings -> imageStrings }
            .flatMap { imageString ->
                getPhotoUrl(Uri.parse(imageString), imageCategory, userUid)!!.subscribeOn(
                    Schedulers.io()
                ).toObservable()
            }
            .toList().toFlowable()
    }

    private fun getPhotoUrl(uri: Uri, imageCategory: String, userUid: String): Single<String>? {
        val photoRef = mFirebaseStorage.reference
            .child("photos").child("products").child(imageCategory).child(userUid)
            .child(uri.lastPathSegment!!)

        return RxFirebaseStorage.putFile(photoRef, uri)
            .map { taskSnapshot -> taskSnapshot.metadata!!.path }
    }
}
