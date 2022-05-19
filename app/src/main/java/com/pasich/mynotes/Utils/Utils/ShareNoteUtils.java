package com.pasich.mynotes.Utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.pasich.mynotes.R;

public class ShareNoteUtils {

  /**
   * Method for calling the share note window
   *
   * @param activity - activity
   * @param textValue - value notes
   */
  public static void shareNotes(Activity activity, String textValue) {
    Intent intent;
    if (!(textValue.length() == 0)) {
      intent =
          new Intent("android.intent.action.SEND")
              .setType("plain/text")
              .putExtra("android.intent.extra.TEXT", textValue);
      activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.share)));

    } else {
      Toast.makeText(activity, R.string.shareNull, Toast.LENGTH_SHORT).show();
    }
  }
}
