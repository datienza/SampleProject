package com.test.sample.di.activity;

/**
 * Created by datienza on 31/01/2017
 */
public interface ActivityComponentBuilder<M extends ActivityModule, C extends ActivityComponent> {
    ActivityComponentBuilder<M, C> activityModule(M activityModule);
    C build();
}
