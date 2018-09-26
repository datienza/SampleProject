package com.test.sample.di.activity;

import android.app.Activity;

import dagger.MembersInjector;

/**
 * Created by datienza on 31/01/2017
 */
public interface ActivityComponent<A extends Activity> extends MembersInjector<A> {
}
