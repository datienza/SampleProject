package com.farmdrop.customer.di.view

import com.farmdrop.customer.ui.products.CarouselProductView
import com.farmdrop.customer.ui.products.CarouselProductViewComponent
import com.farmdrop.customer.ui.products.ProductsView
import com.farmdrop.customer.ui.products.ProductsViewComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewKey

@Module(subcomponents = [
    ProductsViewComponent::class,
    CarouselProductViewComponent::class
])
abstract class ProducerDetailsActivityViewBindingModule {
    @Binds
    @IntoMap
    @ViewKey(ProductsView::class)
    abstract fun productsViewComponent(impl: ProductsViewComponent.Builder): ViewComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ViewKey(CarouselProductView::class)
    abstract fun carouselProductViewComponent(impl: CarouselProductViewComponent.Builder): ViewComponentBuilder<*, *>
}