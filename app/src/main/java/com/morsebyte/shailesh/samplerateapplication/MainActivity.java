package com.morsebyte.shailesh.samplerateapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.morsebyte.shailesh.twostagerating.FeedbackReceivedListener;
import com.morsebyte.shailesh.twostagerating.TwoStageRate;

public class MainActivity extends AppCompatActivity {

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

                //TwoStageRate.with(MainActivity.this).showRatePromptDialog();
                TwoStageRate.with(MainActivity.this).incrementEvent();


            }
        });

        TwoStageRate  twoStageRate =  TwoStageRate.with(MainActivity.this);
        twoStageRate.setFeedbackReceivedListener(new FeedbackReceivedListener() {
            @Override
            public void onFeedbackReceived(String feedback) {
                Toast.makeText(MainActivity.this, feedback, Toast.LENGTH_SHORT).show();
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

        twoStageRate.setInstallDays(5).setEventsTimes(5).setLaunchTimes(5);




        twoStageRate.setFeedbackReceivedListener(new FeedbackReceivedListener() {
            @Override
            public void onFeedbackReceived(String feedback) {
                Toast.makeText(MainActivity.this, feedback, Toast.LENGTH_SHORT).show();
            }
        });
        twoStageRate.showIfMeetsConditions();


    }
}
