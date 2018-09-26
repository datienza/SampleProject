package com.test.sample.di.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by datienza on 31/01/2017
 */
public interface HasFragmentSubcomponentBuilders {
    FragmentComponentBuilder getFragmentComponentBuilder(Class<? extends Fragment> fragmentClass);
}
