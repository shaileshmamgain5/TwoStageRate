package com.morsebyte.shailesh.twostagerating.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.morsebyte.shailesh.twostagerating.AppRateDataModel;
import com.morsebyte.shailesh.twostagerating.IntentHelper;
import com.morsebyte.shailesh.twostagerating.R;
import com.morsebyte.shailesh.twostagerating.TwoStageRate;

/**
 * Created by GuestHouser on 2/8/16.
 */
public class ConfirmRateDialog {

    public Dialog getConfirmRateDialog(final Context context, final AppRateDataModel appRateData)
    {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_rate);

        // set the custom dialog components - text, image and button
        TextView title = (TextView) dialog.findViewById(R.id.tvConfirmRateTitle);
        title.setText(appRateData.getConfirmRateTitle());
        TextView text = (TextView) dialog.findViewById(R.id.tvConfirmRateText);
        text.setText(appRateData.getConfirmRateText());
        TextView deny = (TextView) dialog.findViewById(R.id.tvConfirmDeny);
        deny.setText(appRateData.getConfirmRateNegativeText());
        TextView submit = (TextView) dialog.findViewById(R.id.tvConfirmSubmit);
        submit.setText(appRateData.getConfirmRatePositiveText());
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
                final Intent intentToAppstore = appRateData.getStoreType() == TwoStageRate.StoreType.GOOGLEPLAY ?
                        IntentHelper.createIntentForGooglePlay(context) : IntentHelper.createIntentForAmazonAppstore(context);
                context.startActivity(intentToAppstore);
                dialog.dismiss();

            }
        });


        return  dialog;
    }
}
