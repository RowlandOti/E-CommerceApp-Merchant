package com.rowland.delivery.features.dash.presentation.viewmodels.product;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.rowland.delivery.features.dash.domain.models.product.Product;
import com.rowland.delivery.features.dash.domain.usecases.product.CreateProductUseCase;
import com.rowland.delivery.features.dash.domain.usecases.product.LoadProductsUseCase;
import com.rowland.delivery.features.dash.presentation.viewmodels.SharedViewModel;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rowland on 5/13/2018.
 */

public class ProductViewModel extends SharedViewModel<Product> {

    private final MutableLiveData<String> firebaseUserUid = new MutableLiveData<>();


    private final LoadProductsUseCase loadProductsUseCase;

    public ProductViewModel(LoadProductsUseCase loadProductsUseCase) {
        this.loadProductsUseCase = loadProductsUseCase;
    }


    public void setFirebaseUserUid(String userUid) {
        firebaseUserUid.setValue(userUid);
    }

    @Override
    public LiveData<List<Product>> getDataList() {
        this.loadProductsUseCase.loadProducts(this.firebaseUserUid.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products -> {
                    dataList.setValue(products);
                }, throwable -> {
                    Log.d(ProductViewModel.class.getSimpleName(), "Error Retrieving List: " + throwable);
                }, () -> {
                    Log.d(ProductViewModel.class.getSimpleName(), "Done Retrieving");
                });
        return dataList;
    }
}
