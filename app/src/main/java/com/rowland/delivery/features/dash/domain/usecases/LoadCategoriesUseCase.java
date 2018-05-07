package com.rowland.delivery.features.dash.domain.usecases;

import com.google.firebase.firestore.QuerySnapshot;
import com.rowland.delivery.features.dash.data.model.CategoryRepository;
import com.rowland.delivery.features.dash.domain.models.Category;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

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
