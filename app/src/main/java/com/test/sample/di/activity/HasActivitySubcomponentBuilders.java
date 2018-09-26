package com.test.sample.di.activity;

import android.app.Activity;

/**
 * Created by datienza on 31/01/2017
 */
public interface HasActivitySubcomponentBuilders {
    ActivityComponentBuilder getActivityComponentBuilder(Class<? extends Activity> activityClass);
}
