package com.rowland.delivery.features.dash.domain.usecases.product;

import com.rowland.delivery.features.dash.data.model.product.ProductRepository;
import com.rowland.delivery.features.dash.domain.models.product.Product;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Rowland on 5/13/2018.
 */

public class LoadProductsUseCase {

    private ProductRepository productRepository;

    @Inject
    public LoadProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flowable<List<Product>> loadProducts(String userUID, String productCategory) {
        return this.productRepository.getProducts(userUID, productCategory);
    }
}
