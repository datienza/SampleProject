package com.farmdrop.customer.di.fragment

import com.farmdrop.customer.ui.producers.ProducersFragment
import com.farmdrop.customer.ui.producers.ProducersFragmentComponent
import com.farmdrop.customer.ui.products.CategoriesFragment
import com.farmdrop.customer.ui.products.CategoriesFragmentComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.farmdrop.library.di.fragment.FragmentComponentBuilder
import uk.co.farmdrop.library.di.fragment.FragmentKey

@Module(subcomponents = [
    CategoriesFragmentComponent::class,
    ProducersFragmentComponent::class
])
abstract class ProductsActivityFragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(CategoriesFragment::class)
    abstract fun categoresListFragmentComponentBuilder(impl: CategoriesFragmentComponent.Builder): FragmentComponentBuilder<*, *>

    @Binds
    @IntoMap
    @FragmentKey(ProducersFragment::class)
    abstract fun producersListFragmentComponentBuilder(impl: ProducersFragmentComponent.Builder): FragmentComponentBuilder<*, *>
}
