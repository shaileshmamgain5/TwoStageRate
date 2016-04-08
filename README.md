# TwoStageRate
TwoStageRate is a library to help you promote your android app by prompting users to rate the app after using it for a few days. Also its two stage process ensures higher reviews to go to playstore while getting useful feedback on lower ratings.

# Install
You can download from maven central.

#  Usage

To put it simply, it can be done just in two lines of code with default settings.

` TwoStageRate twoStageRate = TwoStageRate.with(this);
         twoStageRate.showIfMeetsConditions();`
         
Also one can set basic settings like

` TwoStageRate twoStageRate = TwoStageRate.with(this);
    twoStageRate.setInstallDays(5).setEventsTimes(5).setLaunchTimes(5);twoStageRate.resetOnDismiss(true);
         twoStageRate.showIfMeetsConditions();`
         
 On receiving feedback, any action can be done on the feedback using feedbacklistener
 
 `twoStageRate.setFeedbackReceivedListener(new FeedbackReceivedListener() {
              @Override
              public void onFeedbackReceived(String feedback) {
                  Toast.makeText(MainActivity.this, feedback, Toast.LENGTH_SHORT).show();`
                  
                  
 However if you want to costumize all three diologs as per your app specific text, you can do it like this
 
 ` TwoStageRate twoStageRate = TwoStageRate.with(this);
  
          twoStageRate.setInstallDays(5).setEventsTimes(5).setLaunchTimes(5);
  
          twoStageRate.resetOnDismiss(true);
  
  
          
  
  
          twoStageRate.setFeedbackReceivedListener(new FeedbackReceivedListener() {
              @Override
              public void onFeedbackReceived(String feedback) {
                  Toast.makeText(MainActivity.this, feedback, Toast.LENGTH_SHORT).show();
              }
          });
  
  
         /* *
           * Provide your custom text on initiial rate prompt dialog*/
           
          twoStageRate.with(this).setRatePromptTitle("INITIAL_TITLE").
                  setRatePromptLaterText("LATER_TEXT").setRatePromptNeverText("NEVER_TEXT").setRatePromptDismissible(false);
  
          /**
           * provide custom text on the confirmation dialog*/
           
          twoStageRate.with(this).setConfirmRateDialogTitle("CONFIRMATION_TITLE").setConfirmRateDialogDescription("CONFIRMATION_DESCRITPION").
                  setConfirmRateDialogPositiveText("POSITIVE_BUTTON_TEXT").setConfirmRateDialogNegativeText("NEGATIVE_BUTTON_TEXT").setConfirmRateDialogDismissible(true);
  
          /**
           * provide custom text on feedback dialog*/
           
          twoStageRate.with(this).setFeedbackDialogTitle("FEEDBACK_TITLE").setFeedbackDialogDescription("FEEDBACK_DIALOG_DESCRIPTION").
                  setFeedbackDialogPositiveText("POSITIVE_BUTTON_TEXT").setFeedbackDialogNegativeText("NEGATIVE_BUTTON_TEXT").setFeedbackDialogDismissible(false);
  
  twoStageRate.showIfMeetsConditions();
`
         
         

