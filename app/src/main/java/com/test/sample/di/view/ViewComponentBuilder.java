package com.test.sample.di.view;

/**
 * Created by datienza on 31/01/2017
 */
public interface ViewComponentBuilder<M extends ViewModule, C extends ViewComponent> {
    ViewComponentBuilder<M, C> viewModule(M viewModule);

    C build();
}
