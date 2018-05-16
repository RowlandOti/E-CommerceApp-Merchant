package com.rowland.delivery.features.dash.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.rowland.delivery.features.dash.data.model.category.CategoryRepository;
import com.rowland.delivery.features.dash.data.model.order.OrderRepository;
import com.rowland.delivery.features.dash.di.scope.category.CategoryScope;
import com.rowland.delivery.features.dash.domain.usecases.category.CreateCategoryUseCase;
import com.rowland.delivery.features.dash.domain.usecases.category.LoadCategoriesUseCase;
import com.rowland.delivery.features.dash.domain.usecases.order.LoadOrdersUseCase;
import com.rowland.delivery.features.dash.domain.usecases.product.CreateProductUseCase;
import com.rowland.delivery.features.dash.domain.usecases.product.LoadProductsUseCase;
import com.rowland.delivery.features.dash.presentation.viewmodels.category.CategoryViewModelFactory;
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModelFactory;
import com.rowland.delivery.features.dash.presentation.viewmodels.product.NewProductViewModelFactory;
import com.rowland.delivery.features.dash.presentation.viewmodels.product.ProductViewModelFactory;
import com.rowland.delivery.merchant.application.di.modules.ContextModule;
import com.rowland.delivery.services.firebase.modules.FirebaseModule;
import com.rowland.delivery.services.session.di.modules.SessionModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(includes = {FirebaseModule.class, ContextModule.class, SessionModule.class})
public class DashModule {

    @Provides
    @Named("order")
    ViewModelProvider.Factory providesOrderViewModelFactory(LoadOrdersUseCase loadOrdersUseCase) {
        return new OrderViewModelFactory(loadOrdersUseCase);
    }

    @Provides
    @Named("product")
    ViewModelProvider.Factory providesProductViewModelFactory(LoadProductsUseCase loadProductsUseCase) {
        return new ProductViewModelFactory(loadProductsUseCase);
    }

    @Provides
    @Named("newproduct")
    ViewModelProvider.Factory providesNewProductViewModelFactory(Context context, CreateProductUseCase createProductUseCase) {
        return new NewProductViewModelFactory(context, createProductUseCase);
    }

    @Provides
    @Named("category")
    ViewModelProvider.Factory providesCategoryViewModelFactory(LoadCategoriesUseCase loadCategoriesUseCase, CreateCategoryUseCase createCategoryUseCase) {
        return new CategoryViewModelFactory(loadCategoriesUseCase, createCategoryUseCase);
    }

    @Provides
    LoadOrdersUseCase providesLoadOrdersUseCase(OrderRepository OrderRepository) {
        return new LoadOrdersUseCase(OrderRepository);
    }

    @Provides
    OrderRepository providesOrderRepository(FirebaseFirestore firebaseFirestone) {
        return new OrderRepository(firebaseFirestone);
    }

    @Provides
    LoadCategoriesUseCase providesLoadCategoriesUseCase(CategoryRepository categoryRepository) {
        return new LoadCategoriesUseCase(categoryRepository);
    }

    @Provides
    CreateCategoryUseCase providesCreateCategoryUseCase(CategoryRepository categoryRepository) {
        return new CreateCategoryUseCase(categoryRepository);
    }

    @Provides
    CategoryRepository providesCategoryRepository(FirebaseFirestore firebaseFirestone) {
        return new CategoryRepository(firebaseFirestone);
    }
}
