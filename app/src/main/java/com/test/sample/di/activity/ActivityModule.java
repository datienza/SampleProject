package com.test.sample.di.activity;

import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by datienza on 31/01/2017
 */
@Module
public abstract class ActivityModule<T extends AppCompatActivity> {
    protected final T activity;

    public ActivityModule(T activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public AppCompatActivity provideActivity() {
        return activity;
    }
}
