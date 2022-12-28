package com.pasich.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.splashscreen.SplashScreen;

import com.pasich.mynotes.ui.helloUI.HelloActivity;
import com.pasich.mynotes.ui.view.activity.MainActivity;
import com.preference.PowerPreference;

import java.util.Objects;

public class RoutingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        splashScreen.setKeepOnScreenCondition(() -> true);
        startNextActivity();
        finish();
    }

    private void startNextActivity() {
        boolean getFirstStart = PowerPreference.getDefaultFile().getBoolean("firstrun", false);
        if (!getFirstStart && getCountFiles() >= 1) {
            startActivity(new Intent(RoutingActivity.this, HelloActivity.class));
        } else {
            startActivity(new Intent(RoutingActivity.this, MainActivity.class));
        }


        finish();
    }

    public int getCountFiles() {
        return Objects.requireNonNull(getFilesDir().listFiles()).length;
    }
}