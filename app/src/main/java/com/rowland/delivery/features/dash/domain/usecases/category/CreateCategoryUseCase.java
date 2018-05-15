package com.rowland.delivery.features.dash.domain.usecases.category;

import com.rowland.delivery.features.dash.data.model.category.CategoryRepository;
import com.rowland.delivery.features.dash.domain.models.category.Category;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Rowland on 5/4/2018.
 */

public class CreateCategoryUseCase {

    private CategoryRepository categoryRepository;

    @Inject
    public CreateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Single<Category> createCategory(Category category, String userUid) {
        return categoryRepository.createCategory(category, userUid);
    }
}
