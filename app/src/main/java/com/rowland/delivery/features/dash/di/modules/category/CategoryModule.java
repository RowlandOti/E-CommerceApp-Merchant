package com.rowland.delivery.features.dash.di.modules.category;

import com.rowland.delivery.features.dash.di.scope.category.CategoryScope;
import com.rowland.delivery.features.dash.presentation.adapters.CategoryAdapter;
import com.rowland.delivery.services.firebase.modules.FirebaseModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(includes = {FirebaseModule.class})
public class CategoryModule {

    @Provides
    @CategoryScope
    CategoryAdapter providesCategoryAdapter() {
        return new CategoryAdapter();
    }

}
