package com.farmdrop.customer.ui.products

import com.farmdrop.customer.presenters.products.ProductViewPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.view.ViewComponent
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewModule
import uk.co.farmdrop.library.di.view.ViewScope

@ViewScope
@Subcomponent(modules = [CarouselProductViewComponent.ProductViewModule::class])
interface CarouselProductViewComponent : ViewComponent<CarouselProductView> {

    @Subcomponent.Builder
    interface Builder : ViewComponentBuilder<ProductViewModule, CarouselProductViewComponent>

    @Module
    class ProductViewModule(view: CarouselProductView) : ViewModule<CarouselProductView>(view) {

        @ViewScope
        @Provides
        fun provideProductViewPresenter(currentBasket: CurrentBasket, currentDeliverySlot: CurrentDeliverySlot, basketRxBus: BasketRxBus, productsRepository: ProductsRepository) =
                ProductViewPresenter(currentBasket, currentDeliverySlot, basketRxBus, productsRepository)
    }
}
