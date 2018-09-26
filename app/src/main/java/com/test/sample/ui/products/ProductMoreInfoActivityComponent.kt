package com.farmdrop.customer.ui.products

import com.farmdrop.customer.presenters.products.ProductMoreInfoActivityPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.activity.ActivityComponent
import uk.co.farmdrop.library.di.activity.ActivityComponentBuilder
import uk.co.farmdrop.library.di.activity.ActivityModule
import uk.co.farmdrop.library.di.activity.ActivityScope

/**
 * Provide ProductMoreInfoActivity dependencies.
 */

@ActivityScope
@Subcomponent(modules = [ProductMoreInfoActivityComponent.ProductMoreInfoActivityModule::class])
interface ProductMoreInfoActivityComponent : ActivityComponent<ProductMoreInfoActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<ProductMoreInfoActivityModule, ProductMoreInfoActivityComponent>

    @Module
    class ProductMoreInfoActivityModule(activity: ProductMoreInfoActivity, private val productGraphQLId: String?) : ActivityModule<ProductMoreInfoActivity>(activity) {

        @Provides
        fun provideProductMoreInfoActivityPresenter(productsRepository: ProductsRepository, currentDeliverySlot: CurrentDeliverySlot): ProductMoreInfoActivityPresenter =
                ProductMoreInfoActivityPresenter(productsRepository, productGraphQLId, currentDeliverySlot)
    }
}
