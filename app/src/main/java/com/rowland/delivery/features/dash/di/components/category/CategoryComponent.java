package com.rowland.delivery.features.dash.di.components.category;

import com.rowland.delivery.features.dash.di.modules.category.CategoryModule;
import com.rowland.delivery.features.dash.di.scope.category.CategoryScope;
import com.rowland.delivery.features.dash.presentation.fragments.CategoryFragment;

import dagger.Subcomponent;

/**
 * Created by Rowland on 5/6/2018.
 */

@CategoryScope
@Subcomponent(modules = CategoryModule.class)
public interface CategoryComponent {
    void inject(CategoryFragment categoryFragment);
}
