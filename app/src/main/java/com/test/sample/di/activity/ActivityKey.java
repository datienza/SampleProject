package com.test.sample.di.activity;

import android.app.Activity;

import dagger.MapKey;

/**
 * Created by datienza on 31/01/2017
 */
@MapKey
public @interface ActivityKey {
    Class<? extends Activity> value();
}
