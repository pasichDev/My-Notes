package com.pasich.mynotes.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Utils.Database.FeedReaderDbHelper;

import java.util.ArrayList;

public class MainModel {
  private final SQLiteDatabase db;
  public Cursor tags;
  public ArrayList<ListNotesModel> notesArray = new ArrayList<>();

  public MainModel(Context context) {
    FeedReaderDbHelper databaseHelper = new FeedReaderDbHelper(context);
    this.db = databaseHelper.getReadableDatabase();
    this.tags = queryTags();

    searchNotes();
  }

  /** Method that queries all tags */
  public Cursor queryTags() {
    return db.rawQuery("SELECT * FROM tags ORDER BY name ASC;", null);
  }

  public void createTag(String nameTag) {
    db.execSQL("INSERT INTO tags (name) VALUES ('" + nameTag + "');");
  }

  public void closeConnection() {
    tags.close();
    db.close();
  }

  public void searchNotes() {
    Cursor testCursor = db.rawQuery("SELECT * FROM notes ORDER BY title ASC;", null);
    while (testCursor.moveToNext()) {
      notesArray.add(
          new ListNotesModel(
              testCursor.getInt(0),
              testCursor.getString(1),
              testCursor.getString(2),
              testCursor.getString(3),
              testCursor.getString(4)));
    }
  }
}
