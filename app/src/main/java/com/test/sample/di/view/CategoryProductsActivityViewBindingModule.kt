package com.farmdrop.customer.di.view

import com.farmdrop.customer.ui.products.DefaultProductView
import com.farmdrop.customer.ui.products.DefaultProductViewComponent
import com.farmdrop.customer.ui.products.ProductsView
import com.farmdrop.customer.ui.products.ProductsViewComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewKey

@Module(subcomponents = [
    ProductsViewComponent::class,
    DefaultProductViewComponent::class
])
abstract class CategoryProductsActivityViewBindingModule {
    @Binds
    @IntoMap
    @ViewKey(ProductsView::class)
    abstract fun productsViewComponent(impl: ProductsViewComponent.Builder): ViewComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ViewKey(DefaultProductView::class)
    abstract fun defaultProductViewComponent(impl: DefaultProductViewComponent.Builder): ViewComponentBuilder<*, *>
}