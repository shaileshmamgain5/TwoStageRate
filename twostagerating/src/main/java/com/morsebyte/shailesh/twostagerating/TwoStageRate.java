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

import java.util.Date;

/**
 * Created by Shailesh on 2/5/16.
 */
public class TwoStageRate {

    private static final String LAUNCH_COUNT = "LAUNCHCOUNT";
    private static final String INSTALL_DAYS = "INSTALLDAYS";
    private static final String REMIND_DAYS = "REMINDDAYS";



    public static AppRateDataModel appRateData = new AppRateDataModel();
    public static RatePromptDialog ratePromptDialog = new RatePromptDialog();
    public static FeedbackDialog feedbackDialog = new FeedbackDialog();
    public static ConfirmRateDialog confirmRateDialog = new ConfirmRateDialog();
    public static Settings settings = new Settings();



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
        Dialog dialog = getRatePromptDialog(mContext, ratePromptDialog, settings.getThresholdRating());
        if(dialog!=null)
        {
            dialog.show();
        }

    }

    public void setInstallDate(int date)
    {

    }
    private boolean isOverLaunchTimes() {
        return Utils.getIntSystemValue(LAUNCH_COUNT, mContext) >= settings.getLaunchTimes();
    }

    //private boolean isOverInstallDate() {
        //return isOverDate(getInstallDate(context), installDate);
    //}

    private boolean isOverRemindDate() {
        return  true ;
       // return isOverDate(getRemindInterval(context), remindInterval);
    }

    public Dialog getRatePromptDialog(final Context context, final RatePromptDialog ratePromptDialog, final float threshold)
    {
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
                if(rating>threshold)
                {
                    ConfirmRateDialog cr = new ConfirmRateDialog();
                    Dialog dialog1 = getConfirmRateDialog(context, confirmRateDialog);
                    if(dialog1!=null)
                    {
                        dialog1.show();
                    }

                }else
                {
                    FeedbackDialog fd = new FeedbackDialog();
                    Dialog dialog1 = getFeedbackDialog(context, feedbackDialog);
                    if(dialog1!=null)
                    {
                        dialog1.show();
                    }
                }
                dialog.dismiss();
            }
        });
        return  dialog;
    }

    public Dialog getConfirmRateDialog(final Context context, final ConfirmRateDialog confirmRateDialog)
    {
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


        return  dialog;
    }


    public Dialog getFeedbackDialog(final Context context, FeedbackDialog feedbackDialog)
    {
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
        final EditText etFeedback = (EditText)dialog.findViewById(R.id.etFeedback);
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
                if(etFeedback.getText()!=null && etFeedback.getText().length()>0)
                {
                    //// TODO: 2/8/16 : Write a callback with the text in it
                    dialog.dismiss();
                }else
                {
                    Toast.makeText(context, "Bro.. Write Something", Toast.LENGTH_LONG).show();
                }
            }
        });


        return  dialog;
    }


}
