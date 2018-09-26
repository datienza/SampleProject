package com.test.sample.utils;

import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

import timber.log.Timber;

/**
 * Wrapper class for the shared preferences so that we can mock different responses in unit tests
 */
public class SharedPreferencesHelper {

    private final SharedPreferences mSharedPreferences;

    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }

    public boolean getBooleanItem(String key, boolean defaultVal) {
        try {
            return mSharedPreferences.getBoolean(key, defaultVal);
        } catch (Exception e) {
            Timber.e(e);
            return defaultVal;
        }
    }

    public void setBooleanItem(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public int getIntItem(String key, int defaultVal) {
        try {
            return mSharedPreferences.getInt(key, defaultVal);
        } catch (Exception e) {
            Timber.e(e);
            return defaultVal;
        }
    }

    public void setIntItem(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public long getLongItem(String key, long defaultVal) {
        try {
            return mSharedPreferences.getLong(key, defaultVal);
        } catch (Exception e) {
            Timber.e(e);
            return defaultVal;
        }
    }

    public void setLongItem(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public String getStringItem(String key, String defaultVal) {
        try {
            return mSharedPreferences.getString(key, defaultVal);
        } catch (Exception e) {
            Timber.e(e);
            return defaultVal;
        }
    }

    public void setStringItem(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public Set<String> getStringSet(String key, Set<String> defaultValues) {
        try {
            return mSharedPreferences.getStringSet(key, defaultValues);
        } catch (Exception e) {
            Timber.e(e);
            return defaultValues;
        }
    }

    public void setStringSet(String key, Set<String> values) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public Map<String, ?> getAllParameters() {
        return mSharedPreferences.getAll();
    }
}
