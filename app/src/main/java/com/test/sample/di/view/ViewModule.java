package com.test.sample.di.view;

import dagger.Module;
import dagger.Provides;

/**
 * Created by datienza on 31/01/2017
 */
@Module
public abstract class ViewModule<T> {
    protected final T view;

    public ViewModule(T view) {
        this.view = view;
    }

    @Provides
    @ViewScope
    public T provideView() {
        return view;
    }
}
