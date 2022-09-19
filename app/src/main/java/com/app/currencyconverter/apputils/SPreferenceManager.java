package com.app.currencyconverter.apputils;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.currencyconverter.R;

public class SPreferenceManager {

    private static SPreferenceManager mInstance;
    private final SharedPreferences mPreferences;
    private final SharedPreferences.Editor mEditor;

    private SPreferenceManager(Context context) {
        mPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static SPreferenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SPreferenceManager(context);
        }
        return mInstance;
    }

    public void setLong(String key, long value) {
        mEditor.putLong(key, value).apply();
    }

    public long getLong(String key, long defaultValue) {
        return mPreferences.getLong(key, defaultValue);
    }
}