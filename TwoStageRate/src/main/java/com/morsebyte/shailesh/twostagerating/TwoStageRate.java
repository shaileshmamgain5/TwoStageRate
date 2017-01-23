package com.morsebyte.shailesh.twostagerating;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.morsebyte.shailesh.twostagerating.dialog.ConfirmRateDialog;
import com.morsebyte.shailesh.twostagerating.dialog.FeedbackDialog;
import com.morsebyte.shailesh.twostagerating.dialog.RatePromptDialog;

import java.util.Date;

/**
 * Created by Shailesh on 2/5/16.
 */
public class TwoStageRate {

    private static final String LAUNCH_COUNT = "TWOSTAGELAUNCHCOUNT";
    private static final String INSTALL_DAYS = "TWOSTAGEINSTALLDAYS";
    private static final String INSTALL_DATE = "TWOSTAGEINSTALLDATE";
    private static final String EVENT_COUNT = "TWOSTAGEEVENTCOUNT";
    private static final String STOP_TRACK = "TWOSTAGESTOPTRACK";
    private static TwoStageRate singleton;
    public AppRateDataModel appRateData = new AppRateDataModel();
    public RatePromptDialog ratePromptDialog = new RatePromptDialog();
    public FeedbackDialog feedbackDialog = new FeedbackDialog();
    public ConfirmRateDialog confirmRateDialog = new ConfirmRateDialog();
    public static Settings settings = new Settings();
    boolean isDebug = false;
    boolean shouldResetOnDismiss = true;
    private Context mContext;


    private static final String SHARED_PREFERENCES_SHOW_ICON_KEY = "TwoStageRateShowAppIcon";
    private static final String SHARED_PREFERENCES_TOTAL_LAUNCH_TIMES = "TwoStageRateTotalLaunchTimes";
    private static final String SHARED_PREFERENCES_TOTAL_EVENTS_COUNT = "TwoStageRateTotalEventCount";
    private static final String SHARED_PREFERENCES_TOTAL_INSTALL_DAYS = "TwoStageRateTotalInstallDays";
    /**
     * same for feedback dialog
     */

    //for callback
    private FeedbackReceivedListener feedbackReceivedListener;
    private DialogDismissedListener dialogDismissedListener;
    private FeedbackWithRatingReceivedListener feedbackWithRatingReceivedListener;

    private TwoStageRate(Context context) {
        this.mContext = context;
    }

    public static TwoStageRate with(Context context) {
        setUpSettingsIfNotExists(context);
        return new TwoStageRate(context);
    }

    /**
     * Sets up setting items if they are in preferences. Else it just sets the default values
     */
    private static void setUpSettingsIfNotExists(Context context) {

        settings.setEventsTimes(Utils.getIntSystemValue(SHARED_PREFERENCES_TOTAL_EVENTS_COUNT, context, 10));
        settings.setInstallDays(Utils.getIntSystemValue(SHARED_PREFERENCES_TOTAL_INSTALL_DAYS, context, 5));
        settings.setLaunchTimes(Utils.getIntSystemValue(SHARED_PREFERENCES_TOTAL_LAUNCH_TIMES, context, 5));

    }

    public TwoStageRate setFeedbackWithRatingReceivedListener(FeedbackWithRatingReceivedListener feedbackWithRatingReceivedListener) {
        this.feedbackWithRatingReceivedListener = feedbackWithRatingReceivedListener;
        return this;
    }

    /**
     * Checks if the conditions are met (anu one ) and shows prompt if yes.
     * But before it checks whether it has already shown the prompt and user has responded
     * <p>
     * Also it always shows up in Debug mode
     */
    public void showIfMeetsConditions() {

        if (!Utils.getBooleanSystemValue(STOP_TRACK, mContext)) {
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

    public void setShowAppIcon(boolean showAppIcon) {
        Utils.setBooleanSystemValue(SHARED_PREFERENCES_SHOW_ICON_KEY, showAppIcon, mContext);
    }

    private boolean checkIfMeetsCondition() {
        return isOverLaunchTimes() ||
                isOverInstallDays() || isOverEventCounts();

    }

    public void showRatePromptDialog() {
        Dialog dialog = getRatePromptDialog(mContext, ratePromptDialog, settings.getThresholdRating());
        if (dialog != null) {
            dialog.show();
        }

    }

    /**
     * Setting install date of app
     */
    public void setInstallDate() {

        if (Utils.getLongSystemValue(INSTALL_DATE, mContext) == 0) {
            //getting the current time in milliseconds, and creating a Date object from it:
            Date date = new Date(System.currentTimeMillis()); //or simply new Date();
            long millis = date.getTime();
            Utils.setLongSystemValue(INSTALL_DATE, millis, mContext);
        }
    }

    private boolean isOverLaunchTimes() {
        int launches = Utils.getIntSystemValue(LAUNCH_COUNT, mContext);
        if (launches >= settings.getLaunchTimes()) {
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
        int eventCount = Utils.getIntSystemValue(EVENT_COUNT, mContext) + 1;
        Utils.setIntSystemValue(EVENT_COUNT, eventCount, mContext);
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
        Utils.setIntSystemValue(LAUNCH_COUNT, 0, mContext);

        Utils.setBooleanSystemValue(STOP_TRACK, false, mContext);

    }

    public Dialog getRatePromptDialog(final Context context, final RatePromptDialog ratePromptDialog, final float threshold) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rate_initial);
        dialog.setCancelable(this.ratePromptDialog.isDismissible());

        // set the custom dialog components - text, image and button
        TextView title = (TextView) dialog.findViewById(R.id.tvRatePromptTitle);
        title.setText(ratePromptDialog.getTitle());
        RatingBar rbRating = (RatingBar) dialog.findViewById(R.id.rbRatePromptBar);
        ImageView ivAppIcon = (ImageView) dialog.findViewById(R.id.ivAppIcon);

        if ((Utils.getBooleanSystemValue(SHARED_PREFERENCES_SHOW_ICON_KEY, context, true))) {
            ivAppIcon.setImageResource(Utils.twoStageGetAppIconResourceId(context));
            ivAppIcon.setVisibility(View.VISIBLE);
        } else {
            ivAppIcon.setVisibility(View.GONE);
        }

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                if (rating > threshold) {
                    ConfirmRateDialog cr = new ConfirmRateDialog();
                    Dialog dialog1 = getConfirmRateDialog(context, confirmRateDialog);
                    if (dialog1 != null) {
                        dialog1.show();
                    }
                } else {
                    FeedbackDialog fd = new FeedbackDialog();
                    Dialog dialog1 = getFeedbackDialog(context, feedbackDialog, new FeedbackReceivedListener() {
                        @Override
                        public void onFeedbackReceived(String feedback) {
                            if (feedbackReceivedListener != null) {
                                feedbackReceivedListener.onFeedbackReceived(feedback);
                            }
                            if (feedbackWithRatingReceivedListener != null) {
                                feedbackWithRatingReceivedListener.onFeedbackReceived(rating, feedback);
                            }
                        }
                    });
                    if (dialog1 != null) {
                        dialog1.show();
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onDialogDismissed();
            }
        });

        return dialog;
    }

    public Dialog getConfirmRateDialog(final Context context, final ConfirmRateDialog confirmRateDialog) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_rate);
        dialog.setCancelable(this.confirmRateDialog.isDismissible());

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

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onDialogDismissed();
            }
        });


        return dialog;
    }

    public Dialog getFeedbackDialog(final Context context, final FeedbackDialog feedbackDialog, final FeedbackReceivedListener feedbackReceivedListener) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(this.feedbackDialog.isDismissible());
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
                    if (feedbackReceivedListener != null) {
                        feedbackReceivedListener.onFeedbackReceived(etFeedback.getText().toString());
                    }
                } else {
                    Toast.makeText(context, "Bro.. Write Something", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onDialogDismissed();
            }
        });


        return dialog;
    }

    /**
     * Setter and getters for rate prompt dialog
     *
     * @param ratePromptTitle
     */

    public TwoStageRate setRatePromptTitle(String ratePromptTitle) {
        this.ratePromptDialog.ratePromptTitle = ratePromptTitle;
        return this;
    }

    public TwoStageRate setRatePromptLaterText(String ratePromptLaterText) {
        this.ratePromptDialog.ratePromptLaterText = ratePromptLaterText;
        return this;
    }

    public TwoStageRate setRatePromptNeverText(String ratePromptNeverText) {
        this.ratePromptDialog.ratePromptNeverText = ratePromptNeverText;
        return this;
    }

    public TwoStageRate setRatePromptDismissible(boolean dismissible) {
        this.ratePromptDialog.dismissible = dismissible;
        return this;
    }

    public TwoStageRate setFeedbackDialogTitle(String feedbackPromptTitle) {
        this.feedbackDialog.feedbackPromptTitle = feedbackPromptTitle;
        return this;
    }

    public TwoStageRate setFeedbackDialogDescription(String feedbackPromptText) {
        this.feedbackDialog.feedbackPromptText = feedbackPromptText;
        return this;
    }

    public TwoStageRate setFeedbackDialogPositiveText(String feedbackPromptPositiveText) {
        this.feedbackDialog.feedbackPromptPositiveText = feedbackPromptPositiveText;
        return this;
    }


    public TwoStageRate setFeedbackDialogNegativeText(String feedbackPromptNegativeText) {
        this.feedbackDialog.feedbackPromptNegativeText = feedbackPromptNegativeText;
        return this;
    }

    public TwoStageRate setFeedbackDialogDismissible(boolean dismissible) {
        this.feedbackDialog.dismissible = dismissible;
        return this;
    }

    public TwoStageRate setFeedbackReceivedListener(FeedbackReceivedListener feedbackReceivedListener) {
        this.feedbackReceivedListener = feedbackReceivedListener;
        return this;
    }

    public TwoStageRate setOnDialogDismissedListener(DialogDismissedListener dialogDismissedListener) {
        this.dialogDismissedListener = dialogDismissedListener;
        return this;
    }

    public void onDialogDismissed() {
        if (shouldResetOnDismiss) {
            resetTwoStage();
        }
        //dialogDismissedListener.onDialogDismissed();
    }

    /*
     *All setters for ConfirmRateDialog
     */

    public TwoStageRate setConfirmRateDialogTitle(String confirmRateTitle) {
        this.confirmRateDialog.confirmRateTitle = confirmRateTitle;
        return this;
    }


    public TwoStageRate setConfirmRateDialogDescription(String confirmRateText) {
        this.confirmRateDialog.confirmRateText = confirmRateText;
        return this;
    }

    public TwoStageRate setConfirmRateDialogNegativeText(String confirmRateNegativeText) {
        this.confirmRateDialog.confirmRateNegativeText = confirmRateNegativeText;
        return this;
    }

    public TwoStageRate setConfirmRateDialogPositiveText(String confirmRatePositiveText) {
        this.confirmRateDialog.confirmRatePositiveText = confirmRatePositiveText;
        return this;
    }

    public TwoStageRate setConfirmRateDialogDismissible(boolean dismissible) {
        this.confirmRateDialog.dismissible = dismissible;
        return this;
    }

    /**
     * Setters for Settings
     */

    public TwoStageRate setInstallDays(int installDays) {
        Utils.setIntSystemValue(SHARED_PREFERENCES_TOTAL_INSTALL_DAYS, installDays, mContext);
        this.settings.installDays = installDays;
        return this;
    }

    public TwoStageRate setLaunchTimes(int launchTimes) {
        Utils.setIntSystemValue(SHARED_PREFERENCES_TOTAL_LAUNCH_TIMES, launchTimes, mContext);
        this.settings.launchTimes = launchTimes;
        return this;
    }

    public TwoStageRate setEventsTimes(int eventsTimes) {
        Utils.setIntSystemValue(SHARED_PREFERENCES_TOTAL_EVENTS_COUNT, eventsTimes, mContext);
        this.settings.eventsTimes = eventsTimes;
        return this;
    }


    public TwoStageRate setThresholdRating(float thresholdRating) {
        this.settings.thresholdRating = thresholdRating;
        return this;
    }

    public TwoStageRate setStoreType(Settings.StoreType storeType) {
        this.settings.storeType = storeType;
        return this;
    }

    public TwoStageRate resetOnDismiss(boolean shouldReset) {
        this.shouldResetOnDismiss = shouldReset;
        return this;
    }


}
