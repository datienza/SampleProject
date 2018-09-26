package com.farmdrop.customer.ui.products

import com.farmdrop.customer.managers.CurrentDeliverySlot
import com.farmdrop.customer.presenters.products.ProductsSearchFragmentPresenter
import com.farmdrop.customer.ui.adapter.products.ProductsAdapter
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.fragment.FragmentComponent
import uk.co.farmdrop.library.di.fragment.FragmentComponentBuilder
import uk.co.farmdrop.library.di.fragment.FragmentModule
import uk.co.farmdrop.library.di.fragment.FragmentScope

/**
 * Provide ProductsSearchFragment dependencies.
 */

@FragmentScope
@Subcomponent(modules = [ProductsSearchFragmentComponent.ProductsSearchFragmentModule::class])
interface ProductsSearchFragmentComponent : FragmentComponent<ProductsSearchFragment> {

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<ProductsSearchFragmentModule, ProductsSearchFragmentComponent>

    @Module
    class ProductsSearchFragmentModule(
        fragment: ProductsSearchFragment,
        private val productViewLocation: ProductViewLocation? = null
    ) :
            FragmentModule<ProductsSearchFragment>(fragment) {

        @Provides
        fun provideProductsSearchFragmentPresenter(productsRepository: ProductsRepository, basketRxBus: BasketRxBus, currentDeliverySlot: CurrentDeliverySlot) =
            ProductsSearchFragmentPresenter(productsRepository, basketRxBus, currentDeliverySlot)

        @Provides
        fun provideProductsAdapter() =
            ProductsAdapter(productViewLocation)
    }
}
