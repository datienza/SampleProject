package com.farmdrop.customer.ui.products

import com.farmdrop.customer.di.view.ProductDetailsActivityViewBindingModule
import com.farmdrop.customer.managers.CurrentBasket
import com.farmdrop.customer.presenters.products.ProductDetailsActivityPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.android.schedulers.AndroidSchedulers
import uk.co.farmdrop.library.di.activity.ActivityComponent
import uk.co.farmdrop.library.di.activity.ActivityComponentBuilder
import uk.co.farmdrop.library.di.activity.ActivityModule
import uk.co.farmdrop.library.di.activity.ActivityScope

/**
 * Provide ProductDetailsActivity dependencies.
 */

@ActivityScope
@Subcomponent(modules = [
    ProductDetailsActivityComponent.ProductDetailsActivityModule::class,
    ProductDetailsActivityViewBindingModule::class])
interface ProductDetailsActivityComponent : ActivityComponent<ProductDetailsActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<ProductDetailsActivityModule, ProductDetailsActivityComponent>

    @Module
    class ProductDetailsActivityModule(activity: ProductDetailsActivity, private val productId: Int, private val graphQLProductId: String?) : ActivityModule<ProductDetailsActivity>(activity) {

        @Provides
        fun provideProductDetailsActivityPresenter(
            productsRepository: ProductsRepository,
            producersRepository: ProducersRepository,
            currentBasket: CurrentBasket,
            currentDeliverySlot: CurrentDeliverySlot,
            basketRxBus: BasketRxBus
        ) = ProductDetailsActivityPresenter(productsRepository,
                productId,
                graphQLProductId,
                AndroidSchedulers.mainThread(),
                producersRepository,
                currentBasket,
                currentDeliverySlot,
                basketRxBus)
    }
}
