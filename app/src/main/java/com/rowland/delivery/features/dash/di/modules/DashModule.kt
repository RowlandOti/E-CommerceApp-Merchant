package com.rowland.delivery.features.dash.di.modules

import com.google.firebase.firestore.FirebaseFirestore
import com.rowland.delivery.features.dash.data.model.category.CategoryRepository
import com.rowland.delivery.features.dash.data.model.order.OrderRepository
import com.rowland.delivery.features.dash.domain.usecases.category.CreateCategoryUseCase
import com.rowland.delivery.features.dash.domain.usecases.category.LoadCategoriesUseCase
import com.rowland.delivery.features.dash.domain.usecases.order.LoadOrdersUseCase
import com.rowland.delivery.merchant.application.di.modules.ContextModule
import com.rowland.delivery.services.firebase.modules.FirebaseModule
import com.rowland.delivery.services.session.di.modules.SessionModule
import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(includes = arrayOf(FirebaseModule::class, ContextModule::class, SessionModule::class, ViewModelFactoryModule::class))
class DashModule {

    @Provides
    internal fun providesLoadOrdersUseCase(OrderRepository: OrderRepository): LoadOrdersUseCase {
        return LoadOrdersUseCase(OrderRepository)
    }

    @Provides
    internal fun providesOrderRepository(firebaseFirestone: FirebaseFirestore): OrderRepository {
        return OrderRepository(firebaseFirestone)
    }

    @Provides
    internal fun providesLoadCategoriesUseCase(categoryRepository: CategoryRepository): LoadCategoriesUseCase {
        return LoadCategoriesUseCase(categoryRepository)
    }

    @Provides
    internal fun providesCreateCategoryUseCase(categoryRepository: CategoryRepository): CreateCategoryUseCase {
        return CreateCategoryUseCase(categoryRepository)
    }

    @Provides
    internal fun providesCategoryRepository(firebaseFirestone: FirebaseFirestore): CategoryRepository {
        return CategoryRepository(firebaseFirestone)
    }
}
