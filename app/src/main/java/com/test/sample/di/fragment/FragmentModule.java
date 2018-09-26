package com.test.sample.di.fragment;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by datienza on 31/01/2017
 */
@Module
public abstract class FragmentModule<T extends Fragment> {
    protected final T fragment;

    public FragmentModule(T fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public T provideFragment() {
        return fragment;
    }
}
