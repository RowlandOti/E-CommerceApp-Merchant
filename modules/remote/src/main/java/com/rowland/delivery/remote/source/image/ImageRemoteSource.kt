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

    override fun uploadImages(imageEntitys: List<String>, imageCategory: String, userUid: String): Flowable<List<String>> {
        return Observable.fromArray(imageEntitys)
                .flatMapIterable({ imageStrings -> imageStrings })
                .flatMap({ imageString -> getPhotoUrl(Uri.parse(imageString), imageCategory, userUid)!!.subscribeOn(Schedulers.io()).toObservable() })
                .toList().toFlowable()
    }

    fun getPhotoUrl(uri: Uri, imageCategory: String, userUid: String): Single<String>? {
        val photoRef = mFirebaseStorage.reference
                .child("photos").child("products").child(imageCategory).child(userUid)
                .child(uri.lastPathSegment!!)

        return RxFirebaseStorage.putFile(photoRef, uri)
                .map { taskSnapshot -> taskSnapshot.metadata!!.path }
    }
}
