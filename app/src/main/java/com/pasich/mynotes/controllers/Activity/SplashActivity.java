package com.pasich.mynotes.controllers.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("splashEnable", true)) {
      initialization();
    } else {
      finish();
      startActivity(new Intent(SplashActivity.this, MainActivity.class));
      overridePendingTransition(0, 0);
    }
  }

  private void initialization() {
    Thread splashScreenStarter =
        new Thread() {
          public void run() {
            try {
              int delay = 0;
              while (delay < 2000) {
                sleep(100);
                delay = delay + 100;
              }
              startActivity(new Intent(SplashActivity.this, MainActivity.class));

            } catch (InterruptedException e) {
              e.printStackTrace();
            } finally {
              finish();
            }
          }
        };
    splashScreenStarter.start();
  }
}
