package com.pasich.mynotes.utils.activity;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import com.pasich.mynotes.R;
import com.pasich.mynotes.di.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public class MainUtils {
    private int mSwipe = 0;

    @Inject
    public MainUtils() {
    }

    /**
     * Method The method that implements the closing of the application
     *
     * @param activity - context (this)
     */
    public void CloseApp(Activity activity) {
        mSwipe = mSwipe + 1;
        if (mSwipe == 1) {
            Toast.makeText(activity, activity.getString(R.string.exitWhat), Toast.LENGTH_SHORT).show();
            new Handler() {{
                postDelayed(() -> {
                    if (mSwipe == 1) {
                        mSwipe = 0;
                    }
                }, 5000);
            }};

        } else if (mSwipe == 2) {
            activity.finish();
            mSwipe = 0;
        }
    }


}
