package com.morsebyte.shailesh.twostagerating;

import android.content.Context;
import android.content.Intent;

import com.morsebyte.shailesh.twostagerating.dialog.UriHelper;

/**
 * Created by GuestHouser on 2/8/16.
 */
public class IntentHelper {

    private static final String GOOGLE_PLAY_PACKAGE_NAME = "com.android.vending";

    private IntentHelper() {
    }

    public static Intent createIntentForGooglePlay(Context context) {
        String packageName = context.getPackageName();
        Intent intent = new Intent(Intent.ACTION_VIEW, UriHelper.getGooglePlay(packageName));
        if (UriHelper.isPackageExists(context, GOOGLE_PLAY_PACKAGE_NAME)) {
            intent.setPackage(GOOGLE_PLAY_PACKAGE_NAME);
        }
        return intent;
    }

    public static Intent createIntentForAmazonAppstore(Context context) {
        String packageName = context.getPackageName();
        return new Intent(Intent.ACTION_VIEW, UriHelper.getAmazonAppstore(packageName));
    }
}
