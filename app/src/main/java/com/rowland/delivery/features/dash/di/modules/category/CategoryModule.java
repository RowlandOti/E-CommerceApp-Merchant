package com.rowland.delivery.features.dash.di.modules.category;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.rowland.delivery.features.dash.data.model.CategoryRepository;
import com.rowland.delivery.features.dash.di.scope.category.Category;
import com.rowland.delivery.features.dash.domain.usecases.CreateCategoryUseCase;
import com.rowland.delivery.features.dash.domain.usecases.LoadCategoriesUseCase;
import com.rowland.delivery.features.dash.presentation.adapters.CategoryAdapter;
import com.rowland.delivery.features.dash.presentation.viewmodels.category.CategoryViewModelFactory;
import com.rowland.delivery.merchant.application.di.modules.ContextModule;
import com.rowland.delivery.services.firebase.modules.FirebaseModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(includes = {FirebaseModule.class})
public class CategoryModule {

    @Provides
    @Category
    CategoryAdapter providesCategoryAdapter() {
        return new CategoryAdapter();
    }

    @Provides
    @Category
    @Named("category")
    ViewModelProvider.Factory providesViewModelFactory(LoadCategoriesUseCase loadCategoriesUseCase, CreateCategoryUseCase createCategoryUseCase) {
        return new CategoryViewModelFactory(loadCategoriesUseCase, createCategoryUseCase);
    }

    @Provides
    @Category
    LoadCategoriesUseCase providesLoadCategoriesUseCase(CategoryRepository categoryRepository) {
        return new LoadCategoriesUseCase(categoryRepository);
    }

    @Provides
    @Category
    CreateCategoryUseCase providesCreateCategoryUseCase(CategoryRepository categoryRepository) {
        return new CreateCategoryUseCase(categoryRepository);
    }

    @Provides
    @Category
    CategoryRepository providesCategoryRepository(FirebaseFirestore firebaseFirestone) {
        return new CategoryRepository(firebaseFirestone);
    }
}
