package com.rowland.delivery.features.dash.presentation.viewmodels.product;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rowland.delivery.features.dash.domain.models.product.Product;
import com.rowland.delivery.features.dash.domain.usecases.product.CreateProductUseCase;
import com.rowland.rxgallery.RxGallery;

import java.util.List;

import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rowland on 5/15/2018.
 */

public class NewProductViewModel extends ViewModel {

    private final MutableLiveData<String> firebaseUserUid = new MutableLiveData<>();
    private final MutableLiveData<List<Uri>> selectedImageUri = new MutableLiveData<>();
    private final CreateProductUseCase createProductUseCase;
    private final Context context;

    public NewProductViewModel(Context context, CreateProductUseCase createProductUseCase) {
        this.context = context;
        this.createProductUseCase = createProductUseCase;
    }

    public void setFirebaseUserUid(String userUid) {
        firebaseUserUid.setValue(userUid);
    }

    public void selectImages() {
        RxGallery.gallery(context, false, RxGallery.MimeType.IMAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uris -> selectedImageUri.setValue(uris), throwable -> {
                    Log.d(NewProductViewModel.class.getSimpleName(), "Error Selecting Images: " + throwable);
                }, () -> {
                    Log.d(NewProductViewModel.class.getSimpleName(), "Done Selecting Images");
                });
    }

    public LiveData<List<Uri>> getImages() {
        return selectedImageUri;
    }

    public Single<Product> saveProduct(Product product) {
        StorageReference photoRef = FirebaseStorage.getInstance()
                .getReference()
                .child("photos")
                .child("products")
                .child(firebaseUserUid.getValue())
                .child(selectedImageUri.getValue().get(0).getLastPathSegment());

        return RxFirebaseStorage.putFile(photoRef, selectedImageUri.getValue().get(0))
                .flatMap(taskSnapshot -> {
                    product.setImageUrl(taskSnapshot.getDownloadUrl().toString());
                    return createProductUseCase.createProduct(product, firebaseUserUid.getValue());
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // ToDo: clean subscriptions
    }
}
