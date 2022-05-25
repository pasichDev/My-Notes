package com.pasich.mynotes.Models;

import android.content.Context;
import android.database.Cursor;

import com.pasich.mynotes.Models.Adapter.NoteItemModel;

import java.util.ArrayList;

public class TrashModel extends ModelBase {

  public ArrayList<NoteItemModel> notesArray = new ArrayList<>();

  public TrashModel(Context context) {
    super(context);
    searchNotes();
  }

  public void searchNotes() {
    Cursor testCursor =
        db.rawQuery("SELECT * FROM " + DbHelper.COLUMN_TRASH + " ORDER BY date DESC;", null);
    while (testCursor.moveToNext()) {
      notesArray.add(
          new NoteItemModel(
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
    db.execSQL("DROP TABLE " + DbHelper.COLUMN_TRASH);
    DbHelper.createTableTrash(db);
  }
}
