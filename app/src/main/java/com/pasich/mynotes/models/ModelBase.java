package com.pasich.mynotes.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pasich.mynotes.utils.Database.DbHelper;

public class ModelBase {
  private SQLiteDatabase db;
  public final DbHelper DbHelper;

  public ModelBase(Context context) {
    this.DbHelper = new DbHelper(context);
    this.db = DbHelper.getReadableDatabase();
  }

  public void closeDB() {
    this.db.close();
  }

  public SQLiteDatabase getDb() {
    return this.db;
  }

  public void getRecreateDb() {
    this.db = DbHelper.getReadableDatabase();
  }

  /**
   * The method that implements the transfer of notes to the trash
   *
   * @param noteID - note item position
   */
  public void notesMove(int noteID, String table1, String table2) {
    db.execSQL(
        "INSERT INTO "
            + table1
            + " (title, value, date, type) SELECT title, value, date, type FROM "
            + table2
            + " WHERE id=?;",
        new String[] {String.valueOf(noteID)});

    db.delete(table2, "id = ?", new String[] {String.valueOf(noteID)});
  }
}
