package com.rowland.delivery.features.dash.presentation.viewmodels.category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.rowland.delivery.features.dash.domain.models.category.Category;
import com.rowland.delivery.features.dash.domain.usecases.category.CreateCategoryUseCase;
import com.rowland.delivery.features.dash.domain.usecases.category.LoadCategoriesUseCase;
import com.rowland.delivery.features.dash.presentation.viewmodels.SharedViewModel;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rowland on 5/14/2018.
 */

public class CategoryViewModel extends SharedViewModel<Category> {

    private final MutableLiveData<String> firebaseUserUid = new MutableLiveData<>();

    private final LoadCategoriesUseCase loadCategoriesUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryViewModel(LoadCategoriesUseCase loadCategoriesUseCase, CreateCategoryUseCase createCategoryUseCase) {
        this.loadCategoriesUseCase = loadCategoriesUseCase;
        this.createCategoryUseCase = createCategoryUseCase;
    }

    public LiveData<List<Category>> getDataList() {
        loadCategoriesUseCase.loadCategories(firebaseUserUid.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    dataList.setValue(categories);
                },throwable -> {
                    Log.d(com.rowland.delivery.features.dash.presentation.viewmodels.category.CategoryViewModel.class.getSimpleName(), "Error Retrieving List: "+throwable);
                },() -> {
                    Log.d(com.rowland.delivery.features.dash.presentation.viewmodels.category.CategoryViewModel.class.getSimpleName(), "Done Retrieving");
                });
        return dataList;
    }

    public void setFirebaseUserUid(String userUid) {
        firebaseUserUid.setValue(userUid);
    }

    public Single<Category> saveCategory(Category category) {
        return createCategoryUseCase.createCategory(category, firebaseUserUid.getValue());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // ToDo: clean subscriptions
    }
}
