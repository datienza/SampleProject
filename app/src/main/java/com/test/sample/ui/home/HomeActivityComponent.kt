package com.farmdrop.customer.ui.home

import com.farmdrop.customer.data.wrapper.UserSessionHandler
import com.farmdrop.customer.di.activity.UIActivityModule
import com.farmdrop.customer.di.app.NAME_ANDROID_SCHEDULER_MAIN_THREAD
import com.farmdrop.customer.di.view.HomeActivityViewBindingModule
import com.farmdrop.customer.managers.BasketManager
import com.farmdrop.customer.presenters.home.HomeActivityPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.Scheduler
import uk.co.farmdrop.library.di.activity.ActivityComponent
import uk.co.farmdrop.library.di.activity.ActivityComponentBuilder
import uk.co.farmdrop.library.di.activity.ActivityModule
import uk.co.farmdrop.library.di.activity.ActivityScope
import javax.inject.Named

@ActivityScope
@Subcomponent(modules = [
    HomeActivityComponent.HomeActivityModule::class,
    UIActivityModule::class,
    DatePickerViewBindingModule::class,
    HomeActivityViewBindingModule::class
])
interface HomeActivityComponent : ActivityComponent<HomeActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<HomeActivityModule, HomeActivityComponent>

    @Module
    class HomeActivityModule(activity: HomeActivity) : ActivityModule<HomeActivity>(activity) {

        @Provides
        fun provideHomeActivityPresenter(
            bottomNavigationActivityTabsManager: BottomNavigationActivityTabsManager,
            ordersRepository: OrdersRepository,
            basketRxBus: BasketRxBus,
            basketManager: BasketManager,
            userSessionHandler: UserSessionHandler,
            @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) uiScheduler: Scheduler
        ) = HomeActivityPresenter(bottomNavigationActivityTabsManager, ordersRepository, basketRxBus, basketManager, userSessionHandler, uiScheduler)
    }
}
