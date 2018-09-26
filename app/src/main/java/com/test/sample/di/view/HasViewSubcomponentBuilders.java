package com.test.sample.di.view;

import android.view.View;

/**
 * Created by datienza on 31/01/2017
 */
public interface HasViewSubcomponentBuilders {
    ViewComponentBuilder getViewComponentBuilder(Class<? extends View> viewClass);
}
