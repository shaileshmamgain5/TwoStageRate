package com.morsebyte.shailesh.twostagerating;

import android.app.Dialog;
import android.content.Context;
import android.provider.Telephony;

import com.morsebyte.shailesh.twostagerating.dialog.RatePromptDialog;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Shailesh on 2/5/16.
 */
public class TwoStageRate {

    private static final String LAUNCH_COUNT = "LAUNCHCOUNT";
    private static final String INSTALL_DAYS = "INSTALLDAYS";
    private static final String REMIND_DAYS = "REMINDDAYS";



    AppRateDataModel appRateData = new AppRateDataModel();

    float thresholdRating=3;
    private Date installDate = new Date();

    private int launchTimes = 10;

    private int remindInterval = 1;

    private int eventsTimes = -1;

    Context mContext;
    RatePromptDialog ratePromptDialog= new RatePromptDialog();

    public enum StoreType {
        GOOGLEPLAY,
        AMAZON
    }

    private static TwoStageRate singleton;


    private TwoStageRate(Context context) {
        this.mContext = context;
    }

    public static TwoStageRate with(Context context) {
        if (singleton == null) {
            synchronized (TwoStageRate.class) {
                if (singleton == null) {
                    singleton = new TwoStageRate(context);
                }
            }
        }
        return singleton;
    }

    public void showIfMeetsConditions()
    {
        if(checkIfMeetsCondition()){
            showRatePromptDialog();

        }else{track();}
    }

    private void track() {

    }

    private boolean checkIfMeetsCondition() {
            //return getIsAgreeShowDialog(context) &&
                   // isOverLaunchTimes() &&
                    //isOverInstallDate() &&
                   // isOverRemindDate();

        return false;
    }

    public void showRatePromptDialog()
    {
        Dialog dialog = ratePromptDialog.getRatePromptDialog(mContext, appRateData, thresholdRating);
        if(dialog!=null)
        {
            dialog.show();
        }

    }

    public void setInstallDate(int date)
    {

    }
    private boolean isOverLaunchTimes() {
        return Utils.getIntSystemValue(LAUNCH_COUNT, mContext) >= launchTimes;
    }

    //private boolean isOverInstallDate() {
        //return isOverDate(getInstallDate(context), installDate);
    //}

    private boolean isOverRemindDate() {
        return  true ;
       // return isOverDate(getRemindInterval(context), remindInterval);
    }

}
