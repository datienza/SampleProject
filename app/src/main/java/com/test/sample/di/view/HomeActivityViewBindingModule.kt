package com.farmdrop.customer.di.view

import com.farmdrop.customer.ui.account.OrderView
import com.farmdrop.customer.ui.account.OrderViewComponent
import com.farmdrop.customer.ui.common.TimeLeftCountdownView
import com.farmdrop.customer.ui.home.SpotlightView
import com.farmdrop.customer.ui.home.SpotlightViewComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewKey

@Module(subcomponents = [
    SpotlightViewComponent::class,
    OrderViewComponent::class,
    TimeLeftCountdownViewComponent::class
])
abstract class HomeActivityViewBindingModule {

    @Binds
    @IntoMap
    @ViewKey(SpotlightView::class)
    abstract fun spotlightViewComponentBuilder(impl: SpotlightViewComponent.Builder): ViewComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ViewKey(OrderView::class)
    abstract fun orderViewComponentBuilder(impl: OrderViewComponent.Builder): ViewComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ViewKey(TimeLeftCountdownView::class)
    abstract fun timeLeftCountdownViewComponentBuilder(impl: TimeLeftCountdownViewComponent.Builder): ViewComponentBuilder<*, *>
}
