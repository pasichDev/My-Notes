package com.pasich.mynotes.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

public class DialogVibrateOpen {
    static long mills = 100L;

    public static void start(Activity activity) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator.hasVibrator()) {
            vibrator.vibrate(mills);
        }
    }
}
