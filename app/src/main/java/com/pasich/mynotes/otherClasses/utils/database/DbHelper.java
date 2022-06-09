package com.pasich.mynotes.otherClasses.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "MyNotes.db";
  private static final int SCHEMA = 1;
  public final String COLUMN_TAGS = "tags";
  public final String COLUMN_NOTES = "notes";
  public final String COLUMN_TRASH = "trash";

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, SCHEMA);
  }

  /** Метод который вызиваеться если нет базы данных и создет таблицы */
  @Override
  public void onCreate(SQLiteDatabase db) {

    db.execSQL("CREATE TABLE " + COLUMN_TAGS + " (name TEXT, visibility INTEGER);");
    db.execSQL(
        "CREATE TABLE "
            + COLUMN_NOTES
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, value TEXT, date TEXT, type TEXT, tag TEXT);");
    createTableTrash(db);
  }

  public void createTableTrash(SQLiteDatabase db) {
    db.execSQL(
        "CREATE TABLE "
            + COLUMN_TRASH
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, value TEXT, date TEXT, type TEXT, tag TEXT);");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onCreate(db);
  }
}
