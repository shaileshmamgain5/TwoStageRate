# TwoStageRate
TwoStageRate is a library to help you promote your android app by prompting users to rate the app after using it for a few days. Also its two stage process ensures higher reviews to go to playstore while getting useful feedback on lower ratings.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Two%20Stage%20Rate-brightgreen.svg?style=social)]()

# Screenshots and stages

<img src="https://raw.githubusercontent.com/shaileshmamgain5/TwoStageRate/master/snapshots/Screenshot_20170102-161620.png" width="200" height="350" />
<img src="https://raw.githubusercontent.com/shaileshmamgain5/TwoStageRate/master/snapshots/Screenshot_20170102-161628.png" width="200" height="350" />
<img src="https://raw.githubusercontent.com/shaileshmamgain5/TwoStageRate/master/snapshots/Screenshot_20170102-161834.png" width="200" height="350" />

**Stage 1)** A dialog is displayed to prompt users to rate your app.

**Stage 2)** If user gives 3 or less rating -> A feedback dialog is shown asking users what went wrong?
         If users gives 4 or more rating -> User is requested to rate your app on playstore.

# Install

For gradle, go to your app.gradle file and inside ` dependencies{}` add :

         compile 'com.morsebyte.shailesh.twostagerating:TwoStageRate:1.0'

Thats it, you are good to go.

#  Usage

To put it simply, it can be done just in one line of code inside your activity's ' onCreate(...'

         TwoStageRate.with(this).twoStageRate.showIfMeetsConditions();

It sets the texts (as in above pics) and conditions (5 days of use or 5 times opening of app or 5 times setting of an custom event) to defaults.
         


**( optional)** If you want to change conditions, you can do this insted inside activity's ' onCreate(..'
    
    TwoStageRate twoStageRate = TwoStageRate.with(this);
    //initialises condintions to 5 days of use, 10 times of launch use or 5 triggers of custom event.
    twoStageRate.setInstallDays(5).setLaunchTimes(10).setEventsTimes(5);
    //If user dismisses it, it simply resets again.
    twoStageRate.resetOnDismiss(true);
    twoStageRate.showIfMeetsConditions();
         

'setEventsTime(int n)' sets this dialog on 'n' custom event triggers (like 'n times user clicks a button/ buys a product etc.'). You can increment this trigger anywhere like this:

         TwoStageRate.with(MainActivity.this).incrementEvent();
         

**( optional)** On receiving feedback, any action can be done on the feedback using feedbacklistener
 
         
         twoStageRate.setFeedbackReceivedListener(new FeedbackReceivedListener() {
              @Override
              public void onFeedbackReceived(String feedback) {
                  Toast.makeText(MainActivity.this, feedback, Toast.LENGTH_SHORT).show();
                  }
              }
                  

**( optional)**However if you want to costumize all three diologs as per your app specific text, you can do it like this (You may want to include it inside such a function to be called from 'onCreate(.. ':
 
         
         initTwoStage {
                  TwoStageRate twoStageRate = TwoStageRate.with(this);                               
                  //Setting conditions
                  twoStageRate.setInstallDays(5).setEventsTimes(5).setLaunchTimes(5);
                  twoStageRate.resetOnDismiss(true);
                  twoStageRate.showIfMeetsConditions();

                  //Setting feedback listener
                  twoStageRate.setFeedbackReceivedListener(new FeedbackReceivedListener() {
                       @Override
                       public void onFeedbackReceived(String feedback) {
                           Toast.makeText(MainActivity.this, feedback, Toast.LENGTH_SHORT).show();
                       }
                   });

                   //Setting texts for initial prompt
                   twoStageRate.with(this).setRatePromptTitle("INITIAL_TITLE").
                           setRatePromptLaterText("LATER_TEXT").setRatePromptNeverText("NEVER_TEXT").setRatePromptDismissible(false);

                   //Setting texts for confirmation dialog
                   twoStageRate.with(this).setConfirmRateDialogTitle("CONFIRMATION_TITLE").
                   setConfirmRateDialogDescription("CONFIRMATION_DESCRITPION").
                   setConfirmRateDialogPositiveText("POSITIVE_BUTTON_TEXT").
                   setConfirmRateDialogNegativeText("NEGATIVE_BUTTON_TEXT").
                   setConfirmRateDialogDismissible(true);

                   //Setting texts for feedback title
                   twoStageRate.with(this).setFeedbackDialogTitle("FEEDBACK_TITLE").
                   setFeedbackDialogDescription("FEEDBACK_DIALOG_DESCRIPTION").
                   setFeedbackDialogPositiveText("POSITIVE_BUTTON_TEXT").
                   setFeedbackDialogNegativeText("NEGATIVE_BUTTON_TEXT").
                   setFeedbackDialogDismissible(false);
             }

Kindly contribute the library to include
1) Custom styles. Or Picking up application's app icon and accent colors.
2) Reporting and fixing bugs

Cheers!         

