package com.pasich.mynotes.Controllers.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.R;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

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
