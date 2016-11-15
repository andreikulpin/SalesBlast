package ru.arcsinus.salesblast;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Andrei on 13.11.2016.
 */

public class Preferences {
    private static final String SETTINGS_NAME = "default_settings";
    private static Preferences sSharedPrefs;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    public static class Keys{
        public static final String APP_KEY = "APP_KEY";
    }

    private Preferences(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, context.MODE_PRIVATE);
    }

    public static Preferences getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new Preferences(context);
        }
        return sSharedPrefs;
    }

    public static Preferences getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }
        return null;
    }

    public void put(String key, String val) {
        doEdit();
        mEditor.putString(key, val);
        doCommit();
    }

    public String getString(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }

    private void doEdit() {
        if (mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

}
