package com.test.sample.di.fragment;

import android.support.v4.app.Fragment;

import dagger.MapKey;

/**
 * Created by datienza on 31/01/2017
 */
@MapKey
public @interface FragmentKey {
    Class<? extends Fragment> value();
}
