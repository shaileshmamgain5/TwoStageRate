package com.morsebyte.shailesh.twostagerating;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Calendar;
import java.util.Date;

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

    public static void setLongSystemValue(String key, long value, Context p_context) {
        SharedPreferences myPrefs = p_context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putLong(key, value);
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
        value = myPrefs.getInt(key, 0);

        return value;
    }
    public static long getLongSystemValue(String key, Context p_context) {
        long value = -1;
        SharedPreferences myPrefs = p_context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        value = myPrefs.getLong(key, 0);

        return value;
    }

    public static boolean getBooleanSystemValue(String key, Context p_context) {
       return getBooleanSystemValue(key, p_context, false);
    }

    public static boolean getBooleanSystemValue(String key, Context p_context, boolean defaultValue) {
        boolean value = false;
        SharedPreferences myPrefs = p_context
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        value = myPrefs.getBoolean(key, defaultValue);

        return value;
    }

    /**
     * Takes out the day from date object
     * @param date
     * @return
     */
    public static Calendar getDatePart(Date date){
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }
    /**
     * This method also assumes endDate >= startDate
     **/
    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    /**
     * retrieves app icon resource id
     * **/
    public static int twoStageGetAppIconResourceId(Context context) {
        int appIconResId = -1;
        String packageName = context.getPackageName();
        final PackageManager pm = context.getPackageManager();
        final ApplicationInfo applicationInfo;
        try {
            applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            appIconResId = applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            //do nothing here
        }
        return appIconResId;
    }
}
