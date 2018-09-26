package com.farmdrop.customer.di.fragment

import com.farmdrop.customer.ui.products.ProductsFragment
import com.farmdrop.customer.ui.products.ProductsFragmentComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.farmdrop.library.di.fragment.FragmentComponentBuilder
import uk.co.farmdrop.library.di.fragment.FragmentKey

@Module(subcomponents = [ProductsFragmentComponent::class])
abstract class CategoryProductsActivityFragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(ProductsFragment::class)
    abstract fun productsFragmentComponentBuilder(impl: ProductsFragmentComponent.Builder): FragmentComponentBuilder<*, *>
}