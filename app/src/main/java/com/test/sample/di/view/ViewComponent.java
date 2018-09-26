package com.test.sample.di.view;

import android.view.View;

import dagger.MembersInjector;

/**
 * Created by datienza on 31/01/2017
 */
public interface ViewComponent<A extends View> extends MembersInjector<A> {
}
