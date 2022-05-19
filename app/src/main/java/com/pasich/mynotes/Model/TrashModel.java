package com.pasich.mynotes.Model;

import android.content.Context;
import android.database.Cursor;

import com.pasich.mynotes.Model.Adapter.ListNotesModel;

import java.util.ArrayList;

public class TrashModel extends ModelBase {

  public ArrayList<ListNotesModel> notesArray = new ArrayList<>();

  public TrashModel(Context context) {
    super(context);
    searchNotes();
  }

  public void searchNotes() {
    Cursor testCursor = db.rawQuery("SELECT * FROM trash ORDER BY date DESC;", null);
    while (testCursor.moveToNext()) {
      notesArray.add(
          new ListNotesModel(
              testCursor.getInt(0),
              testCursor.getString(1),
              testCursor.getString(2),
              testCursor.getString(5)));
    }
    testCursor.close();
  }

}
