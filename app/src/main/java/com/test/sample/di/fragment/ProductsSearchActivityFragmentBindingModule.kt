package com.farmdrop.customer.di.fragment

import com.farmdrop.customer.ui.products.ProductsSearchFragment
import com.farmdrop.customer.ui.products.ProductsSearchFragmentComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.farmdrop.library.di.fragment.FragmentComponentBuilder
import uk.co.farmdrop.library.di.fragment.FragmentKey

@Module(subcomponents = [ProductsSearchFragmentComponent::class])
abstract class ProductsSearchActivityFragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(ProductsSearchFragment::class)
    abstract fun productsSearchFragmentComponentBuilder(impl: ProductsSearchFragmentComponent.Builder): FragmentComponentBuilder<*, *>
}