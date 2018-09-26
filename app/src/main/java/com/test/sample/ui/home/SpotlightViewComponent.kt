package com.farmdrop.customer.ui.home

import com.farmdrop.customer.managers.UserManager
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.view.ViewComponent
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewModule
import uk.co.farmdrop.library.di.view.ViewScope

@ViewScope
@Subcomponent(modules = [SpotlightViewComponent.SpotlightViewModule::class])
interface SpotlightViewComponent : ViewComponent<SpotlightView> {

    @Subcomponent.Builder
    interface Builder : ViewComponentBuilder<SpotlightViewModule, SpotlightViewComponent>

    @Module
    class SpotlightViewModule(view: SpotlightView) : ViewModule<SpotlightView>(view) {

        @Provides
        fun provideSpotlightViewPresenter(
            productsRepository: ProductsRepository,
            producersRepository: ProducersRepository,
            shopFrontRepository: ShopFrontRepository,
            userManager: UserManager,
            currentDeliverySlot: CurrentDeliverySlot
        ) = SpotlightViewPresenter(productsRepository, producersRepository, shopFrontRepository, userManager, currentDeliverySlot)
    }
}
