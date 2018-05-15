package com.rowland.delivery.features.dash.domain.usecases.category;

import com.rowland.delivery.features.dash.data.model.category.CategoryRepository;
import com.rowland.delivery.features.dash.domain.models.category.Category;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Rowland on 5/4/2018.
 */

public class LoadCategoriesUseCase {

    private CategoryRepository categoryRepository;

    @Inject
    public LoadCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flowable<List<Category>> loadCategories(String userUid){
        return categoryRepository.getCategories(userUid);
    }
}
