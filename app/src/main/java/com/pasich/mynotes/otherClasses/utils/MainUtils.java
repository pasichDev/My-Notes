package com.pasich.mynotes.otherClasses.utils;

import android.app.Activity;
import android.widget.Toast;

import com.pasich.mynotes.R;

public class MainUtils {

  private int Swipe = 0;

  /**
   * Method The method that implements the closing of the application
   *
   * @param activity - context (this)
   */
  public void CloseApp(Activity activity) {
    Swipe = Swipe + 1;
    if (Swipe == 1) {
      Toast.makeText(activity, activity.getString(R.string.exitWhat), Toast.LENGTH_SHORT).show();
    } else if (Swipe == 2) {
      activity.finish();
      Swipe = 0;
    }
  }
}
