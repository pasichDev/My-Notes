package com.pasich.mynotes.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.helloUI.HelloActivity;
import com.pasich.mynotes.utils.base.simplifications.AnimationListener;
import com.preference.PowerPreference;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startAnimation();
    }

    private void startAnimation() {


            Animation mAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spalsh_start_activity_animation);
            mAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void animationEnd() {
                    startNextActivity();
                }
            });
            findViewById(R.id.splashImage).startAnimation(mAnimation);

    }


    private void startNextActivity() {
        boolean getFirstStart = PowerPreference.getDefaultFile().getBoolean("firstrun", false);
        if (getFirstStart) {

            Log.wtf("pasic", "start spl: ");
            startActivity(new Intent(SplashActivity.this, HelloActivity.class));
            PowerPreference.getDefaultFile().setBoolean("firstrun", true);
        } else {

            Log.wtf("pasic", "start main: ");
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }


        finish();
    }


}
