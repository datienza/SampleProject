package com.farmdrop.customer.ui.products

import com.farmdrop.customer.data.repository.CategoriesRepository
import com.farmdrop.customer.di.app.NAME_ANDROID_SCHEDULER_MAIN_THREAD
import com.farmdrop.customer.di.fragment.CategoryProductsActivityFragmentBindingModule
import com.farmdrop.customer.di.view.CategoryProductsActivityViewBindingModule
import com.farmdrop.customer.managers.BasketManager
import com.farmdrop.customer.presenters.products.CategoryProductsActivityPresenter
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
 * Provide CategoryProductsActivity dependencies.
 */

@ActivityScope
@Subcomponent(modules = [
    CategoryProductsActivityComponent.CategoryProductsActivityModule::class,
    CategoryProductsActivityFragmentBindingModule::class,
    CategoryProductsActivityViewBindingModule::class
])
interface CategoryProductsActivityComponent : ActivityComponent<CategoryProductsActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<CategoryProductsActivityModule, CategoryProductsActivityComponent>

    @Module
    class CategoryProductsActivityModule(activity: CategoryProductsActivity, private val mCategoryId: Int?) : ActivityModule<CategoryProductsActivity>(activity) {

        @Provides
        fun provideCategoryProductsActivityPresenter(
            categoriesRepository: CategoriesRepository,
            bottomNavigationActivityTabsManager: BottomNavigationActivityTabsManager,
            basketRxBus: BasketRxBus,
            basketManager: BasketManager,
            @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) uiScheduler: Scheduler
        ) = CategoryProductsActivityPresenter(categoriesRepository, mCategoryId, bottomNavigationActivityTabsManager, basketRxBus, basketManager, uiScheduler)
    }
}
