package com.morsebyte.shailesh.twostagerating;

/**
 * Created by Shailesh on 02/03/16.
 */
public interface FeedbackWithRatingReceivedListener {
    void onFeedbackReceived(float rating, String feedback);
}