package com.farmdrop.customer.ui.products

import com.farmdrop.customer.presenters.products.FullScreenPhotoActivityPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import uk.co.farmdrop.library.di.activity.ActivityComponent
import uk.co.farmdrop.library.di.activity.ActivityComponentBuilder
import uk.co.farmdrop.library.di.activity.ActivityModule
import uk.co.farmdrop.library.di.activity.ActivityScope

@ActivityScope
@Subcomponent(modules = [
    FullScreenPhotoActivityComponent.FullScreenPhotoActivityModule::class
])
interface FullScreenPhotoActivityComponent : ActivityComponent<FullScreenPhotoActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<FullScreenPhotoActivityModule, FullScreenPhotoActivityComponent>

    @Module
    class FullScreenPhotoActivityModule(activity: FullScreenPhotoActivity) : ActivityModule<FullScreenPhotoActivity>(activity) {

        @Provides
        fun provideFullScreenPhotoActivityPresenter() = FullScreenPhotoActivityPresenter()
    }
}