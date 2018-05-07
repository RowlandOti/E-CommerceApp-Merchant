package com.rowland.delivery.features.dash.presentation.viewmodels.category;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.rowland.delivery.features.dash.domain.models.Category;
import com.rowland.delivery.features.dash.domain.usecases.CreateCategoryUseCase;
import com.rowland.delivery.features.dash.domain.usecases.LoadCategoriesUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rowland on 5/4/2018.
 */

public class CategoryViewModel extends ViewModel {

    private final MutableLiveData<String> firebaseUserUid = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();

    private final LoadCategoriesUseCase loadCategoriesUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryViewModel(LoadCategoriesUseCase loadCategoriesUseCase, CreateCategoryUseCase createCategoryUseCase) {
        this.loadCategoriesUseCase = loadCategoriesUseCase;
        this.createCategoryUseCase = createCategoryUseCase;
    }

    public void setFirebaseUserUid(String userUid) {
        firebaseUserUid.setValue(userUid);
    }

    public MutableLiveData<String> getFirebaseUserUid() {
        return firebaseUserUid;
    }

    public MutableLiveData<List<Category>> getCategoryList() {
        loadCategoriesUseCase.loadCategories(firebaseUserUid.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    for (Category category: categories) {
                        Log.d(CategoryViewModel.class.getSimpleName(), category.getName());
                    }
                    categoryList.setValue(categories);
                },throwable -> {
                    Log.d(CategoryViewModel.class.getSimpleName(), "Error Retrieving List: "+throwable);
                },() -> {
                    Log.d(CategoryViewModel.class.getSimpleName(), "Done Retrieving");
                });
        return categoryList;
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
