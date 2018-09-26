package com.farmdrop.customer.ui.products

import com.farmdrop.customer.di.app.NAME_ANDROID_SCHEDULER_MAIN_THREAD
import com.farmdrop.customer.di.fragment.ProductsSearchActivityFragmentBindingModule
import com.farmdrop.customer.di.view.ProductSearchActivityViewBindingModule
import com.farmdrop.customer.managers.BasketManager
import com.farmdrop.customer.presenters.products.ProductsSearchActivityPresenter
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
 * Provide ProductsSearchActivity dependencies.
 */

@ActivityScope
@Subcomponent(modules = [
    ProductsSearchActivityComponent.ProductsSearchActivityModule::class,
    ProductsSearchActivityFragmentBindingModule::class,
    ProductSearchActivityViewBindingModule::class
])
interface ProductsSearchActivityComponent : ActivityComponent<ProductsSearchActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<ProductsSearchActivityModule, ProductsSearchActivityComponent>

    @Module
    class ProductsSearchActivityModule(activity: ProductsSearchActivity) : ActivityModule<ProductsSearchActivity>(activity) {

        @Provides
        fun provideProductsSearchActivityPresenter(
            bottomNavigationActivityTabsManager: BottomNavigationActivityTabsManager,
            basketRxBus: BasketRxBus,
            basketManager: BasketManager,
            @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) uiScheduler: Scheduler
        ) = ProductsSearchActivityPresenter(bottomNavigationActivityTabsManager, basketRxBus, basketManager, uiScheduler)
    }
}
