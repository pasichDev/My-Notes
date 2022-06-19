package com.pasich.mynotes.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.notes.Note;

public class ShareNoteUtils {

  private final Note note;
  private final Activity activity;

  public ShareNoteUtils(Note note, Activity activity) {
    this.note = note;
    this.activity = activity;
  }

  /** Method for calling the share note window */
  public void shareNotes() {
    String value = note.getValue();
    if (!(value.length() == 0)) {
      activity.startActivity(
          Intent.createChooser(
              new Intent("android.intent.action.SEND")
                  .setType("plain/text")
                  .putExtra("android.intent.extra.TEXT", value),
              activity.getString(R.string.share)));

    } else {
      Toast.makeText(activity, R.string.shareNull, Toast.LENGTH_SHORT).show();
    }
  }
}
