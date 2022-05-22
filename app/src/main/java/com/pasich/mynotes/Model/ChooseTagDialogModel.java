package com.pasich.mynotes.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;


public class ChooseTagDialogModel extends ModelBase {

  public final String tagNote;
  private final int noteID;
  private final Context context;
  public int selectedPosition;

  public ChooseTagDialogModel(Context context, int noteID) {
    super(context);
    this.context = context;
    this.noteID = noteID;
    this.tagNote = getTagNote();
  }

  @SuppressLint("Recycle")
  public void queryTags(TabLayout tabLayout) {
    tabLayout.addTab(tabLayout.newTab().setText(context.getString(R.string.noTag)));
    int i = 0;
    Cursor cursorTags =
        db.rawQuery("SELECT * FROM " + DbHelper.COLUMN_TAGS + " ORDER BY name ASC;", null);
    while (cursorTags.moveToNext()) {
      i = i + 1;
      if (cursorTags.getString(0).equals(tagNote)) selectedPosition = i;
      tabLayout.addTab(tabLayout.newTab().setText(cursorTags.getString(0)));
    }
  }

  @SuppressLint("Recycle")
  public String getTagNote() {
    Cursor cursorNote =
        db.rawQuery(
            "SELECT tag FROM " + DbHelper.COLUMN_NOTES + " WHERE id = ?;",
            new String[] {String.valueOf(noteID)});
    cursorNote.moveToNext();
    return cursorNote.getString(0);
  }
}
