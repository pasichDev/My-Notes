package com.pasich.mynotes.Utils.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "MyNotes.db"; // название бд
  private static final int SCHEMA = 1; // версия базы данных
  static final String TABLE = "tags"; // название таблицы в бд

  public static final String COLUMN_NAME = "name";

  public FeedReaderDbHelper(Context context) {
    super(context, DATABASE_NAME, null, SCHEMA);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + TABLE + "(" + COLUMN_NAME + " TEXT);");

    // добавление начальных данных
    db.execSQL("INSERT INTO " + TABLE + " (" + COLUMN_NAME + ") VALUES ('Java');");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    onCreate(db);
  }
}
