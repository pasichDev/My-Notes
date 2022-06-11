package com.pasich.mynotes.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pasich.mynotes.R;

public class MainUtils {

  private int Swipe = 0;

  public View addButtonSearchView(Context context, int drawable, int id) {
    ImageButton button = new ImageButton(context);
    button.setImageResource(drawable);
    button.setBackground(null);
    button.setId(id);
    return button;
  }

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
