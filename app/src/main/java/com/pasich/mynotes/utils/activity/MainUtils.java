package com.pasich.mynotes.utils.activity;

import android.app.Activity;
import android.widget.Toast;

import com.pasich.mynotes.R;

public class MainUtils {
    private int mSwipe = 0;

    /**
     * Method The method that implements the closing of the application
     *
     * @param activity - context (this)
     */
    public void CloseApp(Activity activity) {
        mSwipe = mSwipe + 1;
        if (mSwipe == 1) {
            Toast.makeText(activity, activity.getString(R.string.exitWhat), Toast.LENGTH_SHORT).show();
        } else if (mSwipe == 2) {
            activity.finish();
            mSwipe = 0;
        }
    }


}
