package com.pasich.mynotes.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pasich.mynotes.Utils.Database.DbHelper;

public class ModelBase {
  protected final SQLiteDatabase db;
  protected final DbHelper DbHelper;

  public ModelBase(Context context) {
    this.DbHelper = new DbHelper(context);
    this.db = DbHelper.getReadableDatabase();
  }

  protected void closeDB() {
    db.close();
  }
}
