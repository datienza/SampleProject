package com.farmdrop.customer.di.view

import com.farmdrop.customer.ui.common.ProducerCardView
import com.farmdrop.customer.ui.products.CarouselProductView
import com.farmdrop.customer.ui.products.CarouselProductViewComponent
import com.farmdrop.customer.ui.products.ProductDetailsAddToBasketView
import com.farmdrop.customer.ui.products.ProductDetailsAddToBasketViewComponent
import com.farmdrop.customer.ui.products.ProductsView
import com.farmdrop.customer.ui.products.ProductsViewComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewKey

@Module(subcomponents = [
    ProducerCardViewComponent::class,
    ProductDetailsAddToBasketViewComponent::class,
    ProductsViewComponent::class,
    CarouselProductViewComponent::class
])
abstract class ProductDetailsActivityViewBindingModule {

    @Binds
    @IntoMap
    @ViewKey(ProducerCardView::class)
    abstract fun producerCardViewComponentBuilder(impl: ProducerCardViewComponent.Builder): ViewComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ViewKey(ProductDetailsAddToBasketView::class)
    abstract fun provideProductDetailsAddToBasketViewComponentBuilder(impl: ProductDetailsAddToBasketViewComponent.Builder): ViewComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ViewKey(ProductsView::class)
    abstract fun productsViewComponent(impl: ProductsViewComponent.Builder): ViewComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ViewKey(CarouselProductView::class)
    abstract fun carouselProductViewComponent(impl: CarouselProductViewComponent.Builder): ViewComponentBuilder<*, *>
}
