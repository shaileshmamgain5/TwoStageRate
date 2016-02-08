package com.morsebyte.shailesh.twostagerating;

import android.app.Dialog;
import android.content.Context;
import android.provider.Telephony;

import com.morsebyte.shailesh.twostagerating.dialog.RatePromptDialog;

import java.lang.reflect.Field;

/**
 * Created by Shailesh on 2/5/16.
 */
public class TwoStageRate {



    AppRateDataModel appRateData = new AppRateDataModel();
    float thresholdRating=3;

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

    public void showRatePromptDialog()
    {
        Dialog dialog = ratePromptDialog.getRatePromptDialog(mContext, appRateData, thresholdRating);
        if(dialog!=null)
        {
            dialog.show();
        }

    }

}
