package com.morsebyte.shailesh.twostagerating.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.morsebyte.shailesh.twostagerating.AppRateDataModel;
import com.morsebyte.shailesh.twostagerating.R;

/**
 * Created by GuestHouser on 2/8/16.
 */
public class FeedbackDialog {

    public Dialog getFeedbackDialog(final Context context, AppRateDataModel appRateData)
    {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feedback);

        // set the custom dialog components - text, image and button
        TextView title = (TextView) dialog.findViewById(R.id.tvFeedbackTitle);
        title.setText(appRateData.getFeedbackPromptTitle());
        TextView text = (TextView) dialog.findViewById(R.id.tvFeedbackText);
        text.setText(appRateData.getFeedbackPromptText());
        TextView deny = (TextView) dialog.findViewById(R.id.tvFeedbackDeny);
        deny.setText(appRateData.getFeedbackPromptNegativeText());
        final EditText etFeedback = (EditText)dialog.findViewById(R.id.etFeedback);
        TextView submit = (TextView) dialog.findViewById(R.id.tvFeedbackSubmit);
        submit.setText(appRateData.getFeedbackPromptPositiveText());
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
