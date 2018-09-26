package com.farmdrop.customer.di.view

import com.farmdrop.customer.ui.common.ProducerCardView
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewKey

@Module(subcomponents = [
    ProducerCardViewComponent::class
])
abstract class ProductsActivityViewBindingModule {

    @Binds
    @IntoMap
    @ViewKey(ProducerCardView::class)
    abstract fun producerCardViewComponentBuilder(impl: ProducerCardViewComponent.Builder): ViewComponentBuilder<*, *>
}
