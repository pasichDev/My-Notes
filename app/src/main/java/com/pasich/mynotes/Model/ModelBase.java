package com.pasich.mynotes.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pasich.mynotes.Utils.Database.FeedReaderDbHelper;

public class ModelBase {
  protected final SQLiteDatabase db;

  public ModelBase(Context context) {
    this.db = new FeedReaderDbHelper(context).getReadableDatabase();
  }

  protected void closeDB() {
    db.close();
  }
}
