package com.rowland.delivery.features.dash.domain.usecases.product;

import com.rowland.delivery.features.dash.data.model.product.ProductRepository;
import com.rowland.delivery.features.dash.domain.models.category.Category;
import com.rowland.delivery.features.dash.domain.models.product.Product;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Rowland on 5/13/2018.
 */

public class CreateProductUseCase {

    private ProductRepository productRepository;

    @Inject
    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Single<Product> createProduct(Product product, String userUid) {
        return this.productRepository.createPoduct(product, userUid);
    }
}
