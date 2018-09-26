package com.farmdrop.customer.ui.products

import com.farmdrop.customer.presenters.products.OrderProductViewPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.view.ViewComponent
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewModule
import uk.co.farmdrop.library.di.view.ViewScope

@ViewScope
@Subcomponent(modules = [OrderProductViewComponent.OrderProductViewModule::class])
interface OrderProductViewComponent : ViewComponent<OrderProductView> {

    @Subcomponent.Builder
    interface Builder : ViewComponentBuilder<OrderProductViewComponent.OrderProductViewModule, OrderProductViewComponent>

    @Module
    class OrderProductViewModule(view: OrderProductView) : ViewModule<OrderProductView>(view) {

        @ViewScope
        @Provides
        fun provideOrderProductViewPresenter(currentBasket: CurrentBasket, currentDeliverySlot: CurrentDeliverySlot, basketRxBus: BasketRxBus, productsRepository: ProductsRepository) =
                OrderProductViewPresenter(currentBasket, currentDeliverySlot, basketRxBus, productsRepository)
    }
}
