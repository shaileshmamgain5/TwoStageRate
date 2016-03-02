package com.morsebyte.shailesh.samplerateapplication;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import com.morsebyte.shailesh.twostagerating.FeedbackReceivedListener;
import com.morsebyte.shailesh.twostagerating.TwoStageRate;

/**
 * Created by Shailesh on 02/03/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        initTwoStage();
    }

    private void initTwoStage() {
        TwoStageRate twoStageRate = TwoStageRate.with(this);

        twoStageRate.settings.setInstallDays(5);
        twoStageRate.settings.setEventsTimes(5);
        twoStageRate.settings.setLaunchTimes(5);


        twoStageRate.feedbackDialog.setFeedbackReceivedListener(new FeedbackReceivedListener() {
            @Override
            public void onFeedbackReceived(String feedback) {
                Toast.makeText(getApplicationContext(), feedback, Toast.LENGTH_SHORT).show();
            }
        });
        twoStageRate.showIfMeetsConditions();


    }
}
