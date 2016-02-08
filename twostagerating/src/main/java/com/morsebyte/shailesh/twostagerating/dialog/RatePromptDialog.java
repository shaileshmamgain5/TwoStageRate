package com.morsebyte.shailesh.twostagerating.dialog;

import android.app.Dialog;
import android.content.Context;
import android.preference.TwoStatePreference;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.morsebyte.shailesh.twostagerating.AppRateDataModel;
import com.morsebyte.shailesh.twostagerating.R;

/**
 * Created by Shailesh on 2/8/16.
 */
public class RatePromptDialog {


    public Dialog getRatePromptDialog(final Context context, final AppRateDataModel appRateData, final float threshold)
    {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rate_initial);

        // set the custom dialog components - text, image and button
        TextView title = (TextView) dialog.findViewById(R.id.tvRatePromptTitle);
        title.setText(appRateData.getRatePromptTitle());
        TextView text = (TextView) dialog.findViewById(R.id.tvRatePromptText);
        text.setText(appRateData.getRatePromptText());
        RatingBar rbRating = (RatingBar) dialog.findViewById(R.id.rbRatePromptBar);

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating>threshold)
                {
                    ConfirmRateDialog cr = new ConfirmRateDialog();
                    Dialog dialog1 = cr.getConfirmRateDialog(context, appRateData);
                    if(dialog1!=null)
                    {
                        dialog1.show();
                    }

                }else
                {
                    FeedbackDialog fd = new FeedbackDialog();
                    Dialog dialog1 = fd.getFeedbackDialog(context, appRateData);
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

}
