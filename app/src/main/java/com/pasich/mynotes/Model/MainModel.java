package com.pasich.mynotes.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pasich.mynotes.Utils.Database.FeedReaderDbHelper;

public class MainModel {
  private final FeedReaderDbHelper databaseHelper;
  private final SQLiteDatabase db;
  private Cursor tags;

  public MainModel(Context context) {
    this.databaseHelper = new FeedReaderDbHelper(context);
    this.db = databaseHelper.getReadableDatabase();

    queryTags();
  }

  public Cursor getTags() {
    return tags;
  }

  public void queryTags() {
    tags = db.rawQuery("SELECT * FROM tags;", null);
  }
}
