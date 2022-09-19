package com.app.currencyconverter.apputils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPrefHandler {

    private static SharedPrefHandler me;
    private static String sharedPrefName;
    private static Context context;

    private SharedPrefHandler(){}

    public static SharedPrefHandler getInstance(Context cntx, PrefFiles sharedPrefFileName){
        if(me == null){
            me = new SharedPrefHandler();
        }
        context = cntx;
        sharedPrefName = sharedPrefFileName.name();
        return me;
    }

    public String getValue(String key) {
        SharedPreferences prefs  = context.getSharedPreferences(sharedPrefName, MODE_PRIVATE);
        return prefs.getString(key, null);
    }

    public Long getLongValue(String key) {
        SharedPreferences prefs  = context.getSharedPreferences(sharedPrefName, MODE_PRIVATE);
        return prefs.getLong(key, System.currentTimeMillis());
    }

    public boolean getBooleanValue(String key) {
        SharedPreferences prefs  = context.getSharedPreferences(sharedPrefName, MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public SharedPrefHandler add(String key, String value){
        try {
            context.getSharedPreferences(sharedPrefName, MODE_PRIVATE).edit().putString(key, value).apply();
            return me;
        }
        catch (Exception ex){
            throw ex;
        }
    }

    public SharedPrefHandler add(String key, Long value){
        try {
            context.getSharedPreferences(sharedPrefName, MODE_PRIVATE).edit().putLong(key, value).apply();
            return me;
        }
        catch (Exception ex){
            throw ex;
        }
    }

    public SharedPrefHandler add(String key, boolean value) {
        try {
            context.getSharedPreferences(sharedPrefName, MODE_PRIVATE).edit().putBoolean(key, value).apply();
            return me;
        } catch (Exception e) {
            throw e;
        }
    }

    public SharedPrefHandler remove(String key){
        try {
            context.getSharedPreferences(sharedPrefName, MODE_PRIVATE).edit().remove(key).apply();
            return me;
        }
        catch (Exception ex){
            throw ex;
        }
    }

    public enum PrefFiles{
        USER_DETAILS_PREF
    }

    public final class Keys{
        public final static String USER_DETAILS = "user_details";
        public final static String LAST_REFRESHED_TIME = "last_refreshed_time";
        public final static String USER_KYC = "user_kyc_status";
    }
}
