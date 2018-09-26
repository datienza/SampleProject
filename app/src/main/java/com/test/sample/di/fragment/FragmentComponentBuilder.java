package com.test.sample.di.fragment;

/**
 * Created by datienza on 31/01/2017
 */
public interface FragmentComponentBuilder<M extends FragmentModule, C extends FragmentComponent> {
    FragmentComponentBuilder<M, C> fragmentModule(M activityModule);
    C build();
}
