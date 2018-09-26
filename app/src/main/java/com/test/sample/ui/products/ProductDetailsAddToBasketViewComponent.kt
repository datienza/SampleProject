package com.farmdrop.customer.ui.products

import com.farmdrop.customer.presenters.products.ProductDetailsAddToBasketViewPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.view.ViewComponent
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewModule
import uk.co.farmdrop.library.di.view.ViewScope

@ViewScope
@Subcomponent(modules = [ProductDetailsAddToBasketViewComponent.ProductDetailsAddToBasketViewModule::class])
interface ProductDetailsAddToBasketViewComponent : ViewComponent<ProductDetailsAddToBasketView> {

    @Subcomponent.Builder
    interface Builder : ViewComponentBuilder<ProductDetailsAddToBasketViewModule, ProductDetailsAddToBasketViewComponent>

    @Module
    class ProductDetailsAddToBasketViewModule(view: ProductDetailsAddToBasketView) : ViewModule<ProductDetailsAddToBasketView>(view) {

        @Provides
        fun provideProductDetailsAddToBasketViewPresenter() = ProductDetailsAddToBasketViewPresenter()
    }
}