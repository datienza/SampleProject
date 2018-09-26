package com.farmdrop.customer.ui.products

import com.farmdrop.customer.data.repository.ProductsRepository
import com.farmdrop.customer.presenters.products.ProductViewPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.view.ViewComponent
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewModule
import uk.co.farmdrop.library.di.view.ViewScope

@ViewScope
@Subcomponent(modules = [DefaultProductViewComponent.ProductViewModule::class])
interface DefaultProductViewComponent : ViewComponent<DefaultProductView> {

    @Subcomponent.Builder
    interface Builder : ViewComponentBuilder<ProductViewModule, DefaultProductViewComponent>

    @Module
    class ProductViewModule(view: DefaultProductView) : ViewModule<DefaultProductView>(view) {

        @ViewScope
        @Provides
        fun provideProductViewPresenter(currentBasket: CurrentBasket, currentDeliverySlot: CurrentDeliverySlot, basketRxBus: BasketRxBus, productsRepository: ProductsRepository) =
                ProductViewPresenter(currentBasket, currentDeliverySlot, basketRxBus, productsRepository)
    }
}
