package com.morsebyte.shailesh.twostagerating.dialog;

import com.morsebyte.shailesh.twostagerating.FeedbackReceivedListener;

/**
 * Created by GuestHouser on 2/8/16.
 */
public class FeedbackDialog {


    public String feedbackPromptTitle = "We're Really Sorry";
    public String feedbackPromptText = "Could you tell us what problem you faced. This will help us improve.";
    public String feedbackPromptPositiveText = "Submit";
    public String feedbackPromptNegativeText = "No Thanks!";
    public boolean dismissible = true;
    public void setDismissible(boolean dismissible)
    {
        this.dismissible = dismissible;
    }

    public boolean isDismissible()
    {
        return this.dismissible;
    }


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
