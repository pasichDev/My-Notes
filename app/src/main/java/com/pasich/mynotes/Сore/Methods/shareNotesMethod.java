package com.pasich.mynotes.Сore.Methods;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.pasich.mynotes.R;

public class shareNotesMethod {

  public static void shareNotes(Activity activity, String textValue) {
    if (!(textValue.length() == 0)) {
      Intent intent = new Intent("android.intent.action.SEND");
      intent.setType("plain/text");
      intent.putExtra("android.intent.extra.TEXT", textValue);
      activity.startActivity(Intent.createChooser(intent, "Поделится"));

    } else {
      Toast toast = Toast.makeText(activity, R.string.shareNull, Toast.LENGTH_SHORT);
      toast.show();
    }
  }
}
