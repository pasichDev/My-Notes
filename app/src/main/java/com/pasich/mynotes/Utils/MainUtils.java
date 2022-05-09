package com.pasich.mynotes.Utils;

import android.app.Activity;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

public class MainUtils {

  private int Swipe = 0;

  /**
   * Method The method that implements the closing of the application
   *
   * @param activity - context (this)
   */
  public void CloseApp(Activity activity) {

    if (PreferenceManager.getDefaultSharedPreferences(activity)
        .getBoolean("swipeToExit", SystemConstant.Settings_SwipeToExit)) {
      Swipe = Swipe + 1;
      if (Swipe == 1) {
        Toast.makeText(activity, activity.getString(R.string.exitWhat), Toast.LENGTH_SHORT).show();
      } else if (Swipe == 2) {
        activity.finish();
        Swipe = 0;
      }
    } else {
      activity.finish();
    }
  }
}
