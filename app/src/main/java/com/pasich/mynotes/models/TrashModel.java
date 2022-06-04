package com.pasich.mynotes.models;

import android.content.Context;
import android.database.Cursor;

import com.pasich.mynotes.models.adapter.NoteModel;

import java.util.ArrayList;
import java.util.Collections;

public class TrashModel extends ModelBase {

  public ArrayList<NoteModel> notesArray = new ArrayList<>();

  public TrashModel(Context context) {
    super(context);
    initialization();
  }

  private void initialization() {
    searchNotes();
    Collections.sort(notesArray, NoteModel.COMPARE_BY_DATE_REVERSE);
  }

  private void searchNotes() {
    Cursor testCursor = getDb().query(DbHelper.COLUMN_TRASH, null, null, null, null, null, null);
    while (testCursor.moveToNext()) {
      notesArray.add(
          new NoteModel(
              testCursor.getInt(0),
              testCursor.getString(1),
              testCursor.getString(2),
              testCursor.getString(3),
              testCursor.getString(4),
              testCursor.getString(5)));
    }

    testCursor.close();
  }

  public void cleanTrash() {
    getDb().execSQL("DROP TABLE " + DbHelper.COLUMN_TRASH);
    DbHelper.createTableTrash(getDb());
    notesArray.clear();
  }

  public void getUpdateCursor() {
    notesArray.clear();
    searchNotes();
    Collections.sort(notesArray, NoteModel.COMPARE_BY_DATE_REVERSE);
  }
}
