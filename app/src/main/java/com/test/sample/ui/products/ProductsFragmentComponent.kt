package com.farmdrop.customer.ui.products

import com.farmdrop.customer.presenters.products.ProductsFragmentPresenter
import com.farmdrop.customer.ui.adapter.products.ProductsAdapter
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.fragment.FragmentComponent
import uk.co.farmdrop.library.di.fragment.FragmentComponentBuilder
import uk.co.farmdrop.library.di.fragment.FragmentModule
import uk.co.farmdrop.library.di.fragment.FragmentScope

@FragmentScope
@Subcomponent(modules = [ProductsFragmentComponent.ProductsFragmentModule::class])
interface ProductsFragmentComponent : FragmentComponent<ProductsFragment> {

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<ProductsFragmentModule, ProductsFragmentComponent>

    @Module
    class ProductsFragmentModule(
        fragment: ProductsFragment,
        private val name: String? = null,
        private val producerId: Int = 0,
        private val productViewLocation: ProductViewLocation? = null
    ) : FragmentModule<ProductsFragment>(fragment) {

        @Provides
        fun provideProductsFragmentPresenter(productsRepository: ProductsRepository, producersRepository: ProducersRepository, basketRxBus: BasketRxBus, currentDeliverySlot: CurrentDeliverySlot) =
                ProductsFragmentPresenter(productsRepository, producersRepository, basketRxBus, currentDeliverySlot, name, producerId)

        @Provides
        fun provideProductsAdapter() =
            ProductsAdapter(productViewLocation)
    }
}
