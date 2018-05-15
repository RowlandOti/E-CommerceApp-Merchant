package com.rowland.delivery.features.dash.presentation.viewmodels.product;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.rowland.delivery.features.dash.domain.usecases.product.CreateProductUseCase;

import javax.inject.Inject;

/**
 * Created by Rowland on 5/15/2018.
 */

public class NewProductViewModelFactory implements ViewModelProvider.Factory {

    private final CreateProductUseCase createProductUseCase;
    private final Context context;

    @Inject
    public NewProductViewModelFactory(Context context, CreateProductUseCase createProductUseCase) {
        this.context = context;
        this.createProductUseCase = createProductUseCase;
    }

    @Override
    public NewProductViewModel create(Class modelClass) {
        return new NewProductViewModel(context, createProductUseCase);
    }
}
