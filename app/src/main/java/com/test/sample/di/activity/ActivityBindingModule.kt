package com.farmdrop.customer.di.activity

import com.farmdrop.customer.ui.home.HomeActivity
import com.farmdrop.customer.ui.home.HomeActivityComponent
import com.farmdrop.customer.ui.products.CategoryProductsActivity
import com.farmdrop.customer.ui.products.CategoryProductsActivityComponent
import com.farmdrop.customer.ui.products.FullScreenPhotoActivity
import com.farmdrop.customer.ui.products.FullScreenPhotoActivityComponent
import com.farmdrop.customer.ui.products.ProductDetailsActivity
import com.farmdrop.customer.ui.products.ProductDetailsActivityComponent
import com.farmdrop.customer.ui.products.ProductMoreInfoActivity
import com.farmdrop.customer.ui.products.ProductMoreInfoActivityComponent
import com.farmdrop.customer.ui.products.ProductsActivity
import com.farmdrop.customer.ui.products.ProductsActivityComponent
import com.farmdrop.customer.ui.products.ProductsSearchActivity
import com.farmdrop.customer.ui.products.ProductsSearchActivityComponent
import com.test.sample.di.activity.ActivityComponentBuilder
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.multibindings.IntoMap

/**
 * Create an ActivityComponent map to inject activity dependencies.
 */
@Module(subcomponents = [
    HomeActivityComponent::class,
    CategoryProductsActivityComponent::class,
    ProductDetailsActivityComponent::class,
    ProductMoreInfoActivityComponent::class,
    ProductsSearchActivityComponent::class,
    FullScreenPhotoActivityComponent::class
])
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(HomeActivity::class)
    abstract fun homeActivityComponentBuilder(impl: HomeActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(ProductsActivity::class)
    abstract fun productsActivityComponentBuilder(impl: ProductsActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(CategoryProductsActivity::class)
    abstract fun categoryProductsActivityComponentBuilder(impl: CategoryProductsActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(ProductDetailsActivity::class)
    abstract fun productDetailsActivityComponentBuilder(impl: ProductDetailsActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(ProductMoreInfoActivity::class)
    abstract fun productsMoreInfoActivityComponentBuilder(impl: ProductMoreInfoActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(ProductsSearchActivity::class)
    abstract fun productsSearchActivityComponentBuilder(impl: ProductsSearchActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(FullScreenPhotoActivity::class)
    abstract fun fullScreenPhotoActivityComponentBuilder(impl: FullScreenPhotoActivityComponent.Builder): ActivityComponentBuilder<*, *>
}
