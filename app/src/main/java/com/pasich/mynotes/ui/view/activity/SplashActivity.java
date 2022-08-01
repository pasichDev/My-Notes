package com.pasich.mynotes.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.R;
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

    if (!PowerPreference.getDefaultFile().getBoolean("splashEnable", true)) {
      startNextActivity();
    } else {
      Animation mAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spalsh_start_activity_animation);
      mAnimation.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
          startNextActivity();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
      });
      findViewById(R.id.splashImage).startAnimation(mAnimation);

    }
  }


  private void startNextActivity() {
    startActivity(new Intent(SplashActivity.this, MainActivity.class));
    finish();
  }

}
