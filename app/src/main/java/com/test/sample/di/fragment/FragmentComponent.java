package com.test.sample.di.fragment;

import android.support.v4.app.Fragment;

import dagger.MembersInjector;

/**
 * Created by datienza on 31/01/2017
 */
public interface FragmentComponent<A extends Fragment> extends MembersInjector<A> {
}
