package com.rowland.delivery.features.dash.presentation.viewmodels.category;

import android.arch.lifecycle.ViewModelProvider;

import com.rowland.delivery.features.dash.domain.usecases.CreateCategoryUseCase;
import com.rowland.delivery.features.dash.domain.usecases.LoadCategoriesUseCase;

import javax.inject.Inject;

/**
 * Created by Rowland on 5/6/2018.
 */

public class CategoryViewModelFactory implements ViewModelProvider.Factory {

    private final LoadCategoriesUseCase loadCategoriesUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;

    @Inject
    public CategoryViewModelFactory(LoadCategoriesUseCase loadCategoriesUseCase, CreateCategoryUseCase createCategoryUseCase) {
        this.loadCategoriesUseCase = loadCategoriesUseCase;
        this.createCategoryUseCase = createCategoryUseCase;
    }

    @Override
    public CategoryViewModel create(Class modelClass) {
        return new CategoryViewModel(loadCategoriesUseCase, createCategoryUseCase);
    }
}
