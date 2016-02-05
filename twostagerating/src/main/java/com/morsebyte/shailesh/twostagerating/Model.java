package com.morsebyte.shailesh.twostagerating;

/**
 * Created by GuestHouser on 2/5/16.
 */
public class Model {
    String ratePromptTitle = "Rate Us";
    String ratePromptText = "How would you rate our application?";
    String ratePromptLaterText= "Remind me later";
    String ratePromptNeverText = "Never show again";
    String confirmRateTitle = "Thank you!";
    String confirmRateText = "Would you like to post your review on app store. This will help and motivate us a lot.";
    String confirmRateNegativeText = "No";
    String confirmRatePositiveText = "Sure";
    String feedbackPromptTitle = "We're Really Sorry";
    String feedbackPromptText = "Could you tell us what problem you faced. This will help us improve.";
    String feedbackPromptPositiveText = "Send";
    String feedbackPromptNegativeText = "No Thanks!";
    TwoStageRate.StoreType storeType = TwoStageRate.StoreType.GOOGLEPLAY;

    public void setRatePromptTitle(String ratePromptTitle)
    {
        this.ratePromptTitle =ratePromptTitle;
    }
    public String getRatePromptTitle( )
    {
        return this.ratePromptTitle;
    }

    public void setRatePromptText(String ratePromptText)
    {
        this.ratePromptText =ratePromptText;
    }
    public String getRatePromptText( )
    {
        return this.ratePromptText;
    }

    public void setRatePromptLaterText(String ratePromptLaterText)
    {
        this.ratePromptLaterText =ratePromptLaterText;
    }
    public String getRatePromptLaterText( )
    {
        return this.ratePromptLaterText;
    }

    public void setRatePromptNeverText(String ratePromptNeverText)
    {
        this.ratePromptNeverText =ratePromptNeverText;
    }
    public String getRatePromptNeverText( )
    {
        return this.ratePromptNeverText;
    }


    public void setConfirmRateTitle(String confirmRateTitle)
    {
        this.confirmRateTitle =confirmRateTitle;
    }
    public String getConfirmRateTitle( )
    {
        return this.confirmRateTitle;
    }

    public void setConfirmRateText(String confirmRateText)
    {
        this.confirmRateText =confirmRateText;
    }
    public String getConfirmRateText( )
    {
        return this.confirmRateText;
    }
    public void setConfirmRateNegativeText(String confirmRateNegativeText)
    {
        this.confirmRateNegativeText =confirmRateNegativeText;
    }
    public String getConfirmRateNegativeText( )
    {
        return this.confirmRateNegativeText;
    }
    public void setConfirmRatePositiveText(String confirmRatePositiveText)
    {
        this.confirmRatePositiveText =confirmRatePositiveText;
    }
    public String getConfirmRatePositiveText( )
    {
        return this.confirmRatePositiveText;
    }

    public void setFeedbackPromptTitle(String feedbackPromptTitle)
    {
        this.feedbackPromptTitle =feedbackPromptTitle;
    }
    public String getFeedbackPromptTitle( )
    {
        return this.feedbackPromptTitle;
    }
    public void setFeedbackPromptText(String feedbackPromptText)
    {
        this.feedbackPromptText =feedbackPromptText;
    }
    public String getFeedbackPromptText( )
    {
        return this.feedbackPromptText;
    }

    public void setFeedbackPromptPositiveText(String feedbackPromptPositiveText)
    {
        this.feedbackPromptPositiveText =feedbackPromptPositiveText;
    }
    public String getFeedbackPromptPositiveText( )
    {
        return this.feedbackPromptPositiveText;
    }

    public void setFeedbackPromptNegativeText(String feedbackPromptNegativeText)
    {
        this.feedbackPromptNegativeText =feedbackPromptNegativeText;
    }
    public String getFeedbackPromptNegativeText( )
    {
        return this.feedbackPromptNegativeText;
    }


    public void setStoreType(TwoStageRate.StoreType storeType)
    {
        this.storeType =storeType;
    }
    public TwoStageRate.StoreType getStoreType( )
    {
        return this.storeType;
    }

}
