package com.farmdrop.customer.ui.products

import com.farmdrop.customer.presenters.products.ProductsViewPresenter
import com.farmdrop.customer.ui.adapter.products.ProductsAdapter
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.view.ViewComponent
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewModule
import uk.co.farmdrop.library.di.view.ViewScope

@ViewScope
@Subcomponent(modules = [ProductsViewComponent.ProductsViewModule::class])
interface ProductsViewComponent : ViewComponent<ProductsView> {

    @Subcomponent.Builder
    interface Builder : ViewComponentBuilder<ProductsViewModule, ProductsViewComponent>

    @Module
    class ProductsViewModule(
        View: ProductsView,
        private val name: String? = null,
        private val producerId: Int = 0,
        private val productViewLocation: ProductViewLocation? = null
    ) : ViewModule<ProductsView>(View) {

        @Provides
        fun provideProductsViewPresenter(productsRepository: ProductsRepository, producersRepository: ProducersRepository, basketRxBus: BasketRxBus, currentDeliverySlot: CurrentDeliverySlot) =
            ProductsViewPresenter(productsRepository, producersRepository, basketRxBus, currentDeliverySlot, name, producerId)

        @Provides
        fun provideProductsAdapter() =
            ProductsAdapter(productViewLocation)
    }
}
