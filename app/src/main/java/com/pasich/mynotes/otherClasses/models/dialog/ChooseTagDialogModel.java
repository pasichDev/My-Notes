package com.pasich.mynotes.otherClasses.models.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;
import com.pasich.mynotes.otherClasses.models.base.ModelBase;

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
    Cursor cursorTags = getDb().query(DbHelper.COLUMN_TAGS, null, null, null, null, null, "name");
    while (cursorTags.moveToNext()) {
      i = i + 1;
      if (cursorTags.getString(0).equals(tagNote)) selectedPosition = i;
      tabLayout.addTab(tabLayout.newTab().setText(cursorTags.getString(0)));
    }
  }

  @SuppressLint("Recycle")
  public String getTagNote() {
    Cursor cursorNote =
        getDb()
            .query(
                DbHelper.COLUMN_NOTES,
                new String[] {"tag"},
                "id =" + noteID,
                null,
                null,
                null,
                null);
    cursorNote.moveToNext();
    return cursorNote.getString(0) == null ? "" : cursorNote.getString(0);
  }
}
