package com.morsebyte.shailesh.samplerateapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.morsebyte.shailesh.twostagerating.FeedbackReceivedListener;
import com.morsebyte.shailesh.twostagerating.FeedbackWithRatingReceivedListener;
import com.morsebyte.shailesh.twostagerating.TwoStageRate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        TwoStageRate.with(MainActivity.this).showIfMeetsConditions();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initTwoStage();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initTwoStage() {
        TwoStageRate twoStageRate = TwoStageRate.with(this);

        twoStageRate.setInstallDays(5).setEventsTimes(3).setLaunchTimes(5);

        twoStageRate.resetOnDismiss(true).resetOnFeedBackDeclined(true).resetOnRatingDeclined(true);

        twoStageRate.setShowAppIcon(true);


        twoStageRate.showIfMeetsConditions();


        /**
         * To receive feedback only, use this listener
         */
        twoStageRate.setFeedbackReceivedListener(new FeedbackReceivedListener() {
            @Override
            public void onFeedbackReceived(String feedback) {
                Toast.makeText(MainActivity.this, feedback, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * To receive rating along with with feedback, use this.
         */
        twoStageRate.setFeedbackWithRatingReceivedListener(new FeedbackWithRatingReceivedListener() {
            @Override
            public void onFeedbackReceived(float rating, String feedback) {
                Toast.makeText(MainActivity.this, "Rating :" + rating + "Feedback :" + feedback, Toast.LENGTH_SHORT).show();
            }
        });


        /**
         *  Provide your custom text on initial rate prompt dialog*/

       //twoStageRate.with(this).setRatePromptTitle("INITIAL_TITLE").
        //        setRatePromptLaterText("LATER_TEXT").setRatePromptNeverText("NEVER_TEXT").setRatePromptDismissible(false);


        /**
         * provide custom text on the confirmation dialog*/

        // twoStageRate.with(this).setConfirmRateDialogTitle("CONFIRMATION_TITLE").setConfirmRateDialogDescription("CONFIRMATION_DESCRITPION").
        //        setConfirmRateDialogPositiveText("POSITIVE_BUTTON_TEXT").setConfirmRateDialogNegativeText("NEGATIVE_BUTTON_TEXT").setConfirmRateDialogDismissible(true);


        /**
         * provide custom text on feedback dialog*/

       // twoStageRate.with(this).setFeedbackDialogTitle("FEEDBACK_TITLE").setFeedbackDialogDescription("FEEDBACK_DIALOG_DESCRIPTION").
         //       setFeedbackDialogPositiveText("POSITIVE_BUTTON_TEXT").setFeedbackDialogNegativeText("NEGATIVE_BUTTON_TEXT").setFeedbackDialogDismissible(false);


    }
}
