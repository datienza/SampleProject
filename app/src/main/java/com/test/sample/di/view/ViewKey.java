package com.test.sample.di.view;

import android.view.View;

import dagger.MapKey;

/**
 * Created by datienza on 31/01/2017
 */
@MapKey
public @interface ViewKey {
    Class<? extends View> value();
}
