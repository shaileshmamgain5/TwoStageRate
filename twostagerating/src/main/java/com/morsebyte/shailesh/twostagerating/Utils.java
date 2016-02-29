package com.morsebyte.shailesh.twostagerating;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GuestHouser on 2/8/16.
 */
public class Utils {

    private static final String SHARED_PREFERENCES_NAME = "TwoStageRate";


    public static void removeSystemValues(Context p_context) {
        SharedPreferences myPrefs = p_context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (myPrefs.getAll().size() > 0) {
            SharedPreferences.Editor prefsEditor = myPrefs.edit();
            prefsEditor.clear();
            prefsEditor.commit();
        }
    }

    public static void setStringSystemValue(String key, String value, Context p_context) {
        SharedPreferences myPrefs = p_context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();

    }

    public static void setIntSystemValue(String key, int value, Context p_context) {
        SharedPreferences myPrefs = p_context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();

    }

    public static void setBooleanSystemValue(String key, boolean value, Context p_context) {
        SharedPreferences myPrefs = p_context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();

    }

    public static String getStringSystemValue(String key, Context p_context) {
        String value = null;
        SharedPreferences myPrefs = p_context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        value = myPrefs.getString(key, null);

        return value;
    }


    public static int getIntSystemValue(String key, Context p_context) {
        int value = -1;
        SharedPreferences myPrefs = p_context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        value = myPrefs.getInt(key, -1);

        return value;
    }
    public static long getLongSystemValue(String key, Context p_context) {
        long value = -1;
        SharedPreferences myPrefs = p_context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        value = myPrefs.getLong(key, -1);

        return value;
    }

    public static boolean getBooleanSystemValue(String key, Context p_context) {
        boolean value = false;
        SharedPreferences myPrefs = p_context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        value = myPrefs.getBoolean(key, false);

        return value;
    }

}
