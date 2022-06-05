package com.pasich.mynotes.utils.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import com.pasich.mynotes.R;
import com.pasich.mynotes.models.ModelBase;

public class ShareNoteUtils extends ModelBase {

  private final int noteID;
  private final Activity activity;

  public ShareNoteUtils(Activity activity, int noteID) {
    super(activity);
    this.noteID = noteID;
    this.activity = activity;
  }

  private String queryValueNote() {
    @SuppressLint("Recycle")
    Cursor cursor = getDb().rawQuery("SELECT value FROM notes WHERE id = " + noteID + " ;", null);
    cursor.moveToNext();
    return cursor.getString(0);
  }

  /** Method for calling the share note window */
  public void shareNotes() {
    String textValue = queryValueNote();
    if (!(textValue.length() == 0)) {
      activity.startActivity(
          Intent.createChooser(
              new Intent("android.intent.action.SEND")
                  .setType("plain/text")
                  .putExtra("android.intent.extra.TEXT", textValue),
              activity.getString(R.string.share)));

    } else {
      Toast.makeText(activity, R.string.shareNull, Toast.LENGTH_SHORT).show();
    }
    closeDB();
  }
}
