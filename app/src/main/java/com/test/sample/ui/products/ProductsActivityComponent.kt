package com.farmdrop.customer.ui.products

import com.farmdrop.customer.di.app.NAME_ANDROID_SCHEDULER_MAIN_THREAD
import com.farmdrop.customer.di.fragment.ProductsActivityFragmentBindingModule
import com.farmdrop.customer.di.view.ProductsActivityViewBindingModule
import com.farmdrop.customer.managers.BasketManager
import com.farmdrop.customer.presenters.products.ProductsActivityPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.Scheduler
import uk.co.farmdrop.library.di.activity.ActivityComponent
import uk.co.farmdrop.library.di.activity.ActivityComponentBuilder
import uk.co.farmdrop.library.di.activity.ActivityModule
import uk.co.farmdrop.library.di.activity.ActivityScope
import javax.inject.Named

/**
 * Provide ProductsActivity dependencies.
 */

@ActivityScope
@Subcomponent(modules = [
    ProductsActivityComponent.ProductsActivityModule::class,
    ProductsActivityFragmentBindingModule::class,
    ProductsActivityViewBindingModule::class
])
interface ProductsActivityComponent : ActivityComponent<ProductsActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<ProductsActivityModule, ProductsActivityComponent>

    @Module
    class ProductsActivityModule(activity: ProductsActivity) : ActivityModule<ProductsActivity>(activity) {

        @Provides
        fun provideProductsActivityPresenter(
            bottomNavigationActivityTabsManager: BottomNavigationActivityTabsManager,
            basketRxBus: BasketRxBus,
            basketManager: BasketManager,
            @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) uiScheduler: Scheduler
        ) = ProductsActivityPresenter(bottomNavigationActivityTabsManager, basketRxBus, basketManager, uiScheduler)
    }
}
