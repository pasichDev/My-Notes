package com.pasich.mynotes.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pasich.mynotes.Utils.Database.FeedReaderDbHelper;

public class MainModel {
  private final SQLiteDatabase db;
  public Cursor tags;

  public MainModel(Context context) {
    FeedReaderDbHelper databaseHelper = new FeedReaderDbHelper(context);
    this.db = databaseHelper.getReadableDatabase();
    this.tags = queryTags();
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
}
