package com.farmdrop.customer.ui.products

import com.farmdrop.customer.data.repository.CategoriesRepository
import com.farmdrop.customer.presenters.products.CategoriesFragmentPresenter
import com.farmdrop.customer.ui.adapter.products.CategoriesAdapter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.fragment.FragmentComponent
import uk.co.farmdrop.library.di.fragment.FragmentComponentBuilder
import uk.co.farmdrop.library.di.fragment.FragmentModule
import uk.co.farmdrop.library.di.fragment.FragmentScope

/**
 * Provide ProductsActivity dependencies.
 */

@FragmentScope
@Subcomponent(modules = [CategoriesFragmentComponent.CategoriesFragmentModule::class])
interface CategoriesFragmentComponent : FragmentComponent<CategoriesFragment> {

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<CategoriesFragmentModule, CategoriesFragmentComponent>

    @Module
    class CategoriesFragmentModule(fragment: CategoriesFragment) : FragmentModule<CategoriesFragment>(fragment) {

        @Provides
        fun provideCategoriesListFragmentPresenter(categoriesRepository: CategoriesRepository) =
            CategoriesFragmentPresenter(categoriesRepository)

        @Provides
        fun provideCategoriesAdapter() = CategoriesAdapter(fragment.context!!)
    }
}
