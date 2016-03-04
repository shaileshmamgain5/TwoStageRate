package com.morsebyte.shailesh.twostagerating;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.morsebyte.shailesh.twostagerating.dialog.ConfirmRateDialog;
import com.morsebyte.shailesh.twostagerating.dialog.FeedbackDialog;
import com.morsebyte.shailesh.twostagerating.dialog.RatePromptDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shailesh on 2/5/16.
 */
public class TwoStageRate {

    private static final String LAUNCH_COUNT = "LAUNCHCOUNT";
    private static final String INSTALL_DAYS = "INSTALLDAYS";
    private static final String INSTALL_DATE = "INSTALLDATE";
    private static final String EVENT_COUNT = "EVENTCOUNT";
    private static final String STOP_TRACK  = "STOPTRACK";

    boolean isDebug = false;


    public AppRateDataModel appRateData = new AppRateDataModel();
    public RatePromptDialog ratePromptDialog = new RatePromptDialog();
    public FeedbackDialog feedbackDialog = new FeedbackDialog();
    public ConfirmRateDialog confirmRateDialog = new ConfirmRateDialog();
    public Settings settings = new Settings();


    private Date installDate = new Date();


    Context mContext;

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

    /**
     * Checks if the conditions are met (anu one ) and shows prompt if yes.
     * But before it checks whether it has already shown the prompt and user has responded
     *
     * Also it always shows up in Debug mode
     */
    public void showIfMeetsConditions() {

        if(!Utils.getBooleanSystemValue(STOP_TRACK,mContext)){
        if (checkIfMeetsCondition() || isDebug) {
            showRatePromptDialog();
            Utils.setBooleanSystemValue(STOP_TRACK, true, mContext);

        } else {
            track();
        }
    }
    }

    private void track() {

    }

    private boolean checkIfMeetsCondition() {
        return isOverLaunchTimes() &&
        isOverInstallDays() && isOverEventCounts();

    }

    public void showRatePromptDialog() {
        Dialog dialog = getRatePromptDialog(mContext, ratePromptDialog, settings.getThresholdRating());
        if (dialog != null) {
            dialog.show();
        }

    }

    public void setInstallDate() {

        if (Utils.getLongSystemValue(INSTALL_DATE, mContext) == 0) {
            //getting the current time in milliseconds, and creating a Date object from it:
            Date date = new Date(System.currentTimeMillis()); //or simply new Date();
            long millis = date.getTime();
            Utils.setLongSystemValue(INSTALL_DATE, millis, mContext);
        }
    }

    private boolean isOverLaunchTimes() {
        if (Utils.getIntSystemValue(LAUNCH_COUNT, mContext) >= settings.getLaunchTimes()) {
            return true;
        } else {
            int count = Utils.getIntSystemValue(LAUNCH_COUNT, mContext) + 1;
            Utils.setIntSystemValue(LAUNCH_COUNT, count, mContext);
            return false;
        }

    }

    private boolean isOverInstallDays() {
        if (Utils.getLongSystemValue(INSTALL_DATE, mContext) == 0) {
            setInstallDate();
            return false;
        } else {
            Date installDate = new Date(Utils.getLongSystemValue(INSTALL_DATE, mContext));
            Date currentDate = new Date(System.currentTimeMillis());
            long days = Utils.daysBetween(installDate, currentDate);
            if (days >= settings.getInstallDays()) {
                return true;
            } else {
                return false;
            }
        }

    }

    private boolean isOverEventCounts() {
        if (Utils.getIntSystemValue(EVENT_COUNT, mContext) >= settings.getEventsTimes()) {
            return true;
        } else {
            return false;
        }
    }

    public void incrementEvent() {
        int eventCount = Utils.getIntSystemValue(EVENT_COUNT, mContext) +1;
        Utils.setIntSystemValue(EVENT_COUNT,eventCount,mContext);
        showIfMeetsConditions();
    }

    public void isDebug(Boolean isDeb) {
        isDebug = isDeb;
    }

    /**
     * It would be called when user says remind me later.
     */
    private void resetTwoStage() {
        //set current date
        //getting the current time in milliseconds, and creating a Date object from it:
        Date date = new Date(System.currentTimeMillis()); //or simply new Date();
        long millis = date.getTime();
        Utils.setLongSystemValue(INSTALL_DATE, millis, mContext);
        Utils.setIntSystemValue(INSTALL_DAYS, 0, mContext);
        Utils.setIntSystemValue(EVENT_COUNT, 0, mContext);

        Utils.setBooleanSystemValue(STOP_TRACK, false, mContext);

    }

    public Dialog getRatePromptDialog(final Context context, final RatePromptDialog ratePromptDialog, final float threshold) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rate_initial);

        // set the custom dialog components - text, image and button
        TextView title = (TextView) dialog.findViewById(R.id.tvRatePromptTitle);
        title.setText(ratePromptDialog.getTitle());
        TextView text = (TextView) dialog.findViewById(R.id.tvRatePromptText);
        text.setText(ratePromptDialog.getDescription());
        RatingBar rbRating = (RatingBar) dialog.findViewById(R.id.rbRatePromptBar);

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating > threshold) {
                    ConfirmRateDialog cr = new ConfirmRateDialog();
                    Dialog dialog1 = getConfirmRateDialog(context, confirmRateDialog);
                    if (dialog1 != null) {
                        dialog1.show();
                    }

                } else {
                    FeedbackDialog fd = new FeedbackDialog();
                    Dialog dialog1 = getFeedbackDialog(context, feedbackDialog);
                    if (dialog1 != null) {
                        dialog1.show();
                    }
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public Dialog getConfirmRateDialog(final Context context, final ConfirmRateDialog confirmRateDialog) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_rate);

        // set the custom dialog components - text, image and button
        TextView title = (TextView) dialog.findViewById(R.id.tvConfirmRateTitle);
        title.setText(confirmRateDialog.getTitle());
        TextView text = (TextView) dialog.findViewById(R.id.tvConfirmRateText);
        text.setText(confirmRateDialog.getDescription());
        TextView deny = (TextView) dialog.findViewById(R.id.tvConfirmDeny);
        deny.setText(confirmRateDialog.getNegativeText());
        TextView submit = (TextView) dialog.findViewById(R.id.tvConfirmSubmit);
        submit.setText(confirmRateDialog.getPositiveText());
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo : emit something here
                dialog.dismiss();
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //// TODO: 2/8/16 : Write a callback
                final Intent intentToAppstore = settings.getStoreType() == Settings.StoreType.GOOGLEPLAY ?
                        IntentHelper.createIntentForGooglePlay(context) : IntentHelper.createIntentForAmazonAppstore(context);
                context.startActivity(intentToAppstore);
                dialog.dismiss();

            }
        });


        return dialog;
    }


    public Dialog getFeedbackDialog(final Context context, final FeedbackDialog feedbackDialog) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feedback);

        // set the custom dialog components - text, image and button
        TextView title = (TextView) dialog.findViewById(R.id.tvFeedbackTitle);
        title.setText(feedbackDialog.getTitle());
        TextView text = (TextView) dialog.findViewById(R.id.tvFeedbackText);
        text.setText(feedbackDialog.getDescription());
        TextView deny = (TextView) dialog.findViewById(R.id.tvFeedbackDeny);
        deny.setText(feedbackDialog.getNegativeText());
        final EditText etFeedback = (EditText) dialog.findViewById(R.id.etFeedback);
        TextView submit = (TextView) dialog.findViewById(R.id.tvFeedbackSubmit);
        submit.setText(feedbackDialog.getPositiveText());
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo : emit something here
                dialog.dismiss();
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFeedback.getText() != null && etFeedback.getText().length() > 0) {
                    //// TODO: 2/8/16 : Write a callback with the text in it
                    dialog.dismiss();
                    feedbackDialog.onFeedbackReceived(etFeedback.getText().toString());
                } else {
                    Toast.makeText(context, "Bro.. Write Something", Toast.LENGTH_LONG).show();
                }
            }
        });


        return dialog;
    }

    /**
     * Setter and getters for rate prompt dialog
     * @param ratePromptTitle
     */

    public void setRatePromptTitle(String ratePromptTitle)
    {
        this.ratePromptDialog.ratePromptTitle =ratePromptTitle;
    }
    public String getRatePromptTitle( )
    {
        return this.ratePromptDialog.ratePromptTitle;
    }

    public void setRatePromptDescription(String ratePromptText)
    {
        this.ratePromptDialog.ratePromptText =ratePromptText;
    }
    public String getRatePromptDescription( )
    {
        return this.ratePromptDialog.ratePromptText;
    }

    public void setRatePromptLaterText(String ratePromptLaterText)
    {
        this.ratePromptDialog.ratePromptLaterText =ratePromptLaterText;
    }
    public String getLaterText( )
    {
        return this.ratePromptDialog.ratePromptLaterText;
    }

    public void setRatePromptNeverText(String ratePromptNeverText)
    {
        this.ratePromptDialog.ratePromptNeverText =ratePromptNeverText;
    }
    public String getRatePromptNeverText( )
    {
        return this.ratePromptDialog.ratePromptNeverText;
    }

    /**same for feedback dialog
     *
     */


}
