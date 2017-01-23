package com.morsebyte.shailesh.samplerateapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.morsebyte.shailesh.twostagerating.FeedbackWithRatingReceivedListener;
import com.morsebyte.shailesh.twostagerating.TwoStageRate;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               TwoStageRate t =  TwoStageRate.with(EventActivity.this);

                        t.setFeedbackWithRatingReceivedListener(new FeedbackWithRatingReceivedListener() {
                            @Override
                            public void onFeedbackReceived(float rating, String feedback) {
                                Toast.makeText(EventActivity.this, String.format("Rating: %f. %s", rating, feedback), Toast.LENGTH_SHORT).show();

                            }
                        }).incrementEvent();
            }
        });
    }

}
