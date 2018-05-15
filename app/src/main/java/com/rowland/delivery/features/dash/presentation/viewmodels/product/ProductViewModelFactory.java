package com.rowland.delivery.features.dash.presentation.viewmodels.product;

import android.arch.lifecycle.ViewModelProvider;

import com.rowland.delivery.features.dash.domain.usecases.product.CreateProductUseCase;
import com.rowland.delivery.features.dash.domain.usecases.product.LoadProductsUseCase;

import javax.inject.Inject;

/**
 * Created by Rowland on 5/13/2018.
 */

public class ProductViewModelFactory implements ViewModelProvider.Factory {

    private final LoadProductsUseCase loadProductsUseCase;

    @Inject
    public ProductViewModelFactory(LoadProductsUseCase loadProductsUseCase) {
        this.loadProductsUseCase = loadProductsUseCase;
    }

    @Override
    public ProductViewModel create(Class modelClass) {
        return new ProductViewModel(loadProductsUseCase);
    }
}
