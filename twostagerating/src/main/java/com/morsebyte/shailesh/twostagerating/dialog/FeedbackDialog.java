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

    String feedbackPromptTitle = "We're Really Sorry";
    String feedbackPromptText = "Could you tell us what problem you faced. This will help us improve.";
    String feedbackPromptPositiveText = "Submit";
    String feedbackPromptNegativeText = "No Thanks!";

    public void setTitle(String feedbackPromptTitle)
    {
        this.feedbackPromptTitle =feedbackPromptTitle;
    }
    public String getTitle( )
    {
        return this.feedbackPromptTitle;
    }
    public void setDescription(String feedbackPromptText)
    {
        this.feedbackPromptText =feedbackPromptText;
    }
    public String getDescription( )
    {
        return this.feedbackPromptText;
    }

    public void setPositiveText(String feedbackPromptPositiveText)
    {
        this.feedbackPromptPositiveText =feedbackPromptPositiveText;
    }
    public String getPositiveText( )
    {
        return this.feedbackPromptPositiveText;
    }

    public void setNegativeText(String feedbackPromptNegativeText)
    {
        this.feedbackPromptNegativeText =feedbackPromptNegativeText;
    }
    public String getNegativeText( )
    {
        return this.feedbackPromptNegativeText;
    }


}
