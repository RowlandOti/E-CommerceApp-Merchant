package com.rowland.delivery.features.dash.di.modules

import androidx.lifecycle.ViewModel
import com.alexfacciorusso.daggerviewmodel.DaggerViewModelInjectionModule
import com.alexfacciorusso.daggerviewmodel.ViewModelKey

import com.rowland.delivery.features.dash.presentation.viewmodels.category.CategoryViewModel
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModel
import com.rowland.delivery.features.dash.presentation.viewmodels.product.EditProductViewModel
import com.rowland.delivery.features.dash.presentation.viewmodels.product.NewProductViewModel
import com.rowland.delivery.features.dash.presentation.viewmodels.product.ProductViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = arrayOf(DaggerViewModelInjectionModule::class))
abstract class ViewModelFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    internal abstract fun bindCategoryViewModel(viewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    internal abstract fun bindOrderViewModel(viewModel: OrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    internal abstract fun bindProductViewModel(viewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewProductViewModel::class)
    internal abstract fun bindNewProductViewModel(viewModel: NewProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProductViewModel::class)
    internal abstract fun bindEditProductViewModel(viewModel: EditProductViewModel): ViewModel
}
