package com.pasich.mynotes.Utils.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "MyNotes.db";
  private static final int SCHEMA = 1;

  public FeedReaderDbHelper(Context context) {
    super(context, DATABASE_NAME, null, SCHEMA);
  }

  /** Метод который вызиваеться если нет базы данных и создет таблицы */
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE tags (name TEXT);");
    db.execSQL(
        "CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, value TEXT, date TEXT, type TEXT, tag TEXT);");

    /* db.execSQL(
        "INSERT INTO notes (title, value, date, type, tag) VALUES ('Title Note 1', 'В методе onUpgrade() происходит обновление схемы БД. В данном случае для примера использован\n"
            + "   * примитивный поход с удалением предыдущей базы данных с помощью sql-выражения DROP и последующим\n"
            + "   * ее созданием. Но в реальности если вам будет необходимо сохранить данные, этот метод может', 1234567, 'Note', '');");
    db.execSQL("INSERT INTO notes  (title, value, date, type, tag) VALUES ('Title Note 2', 'testPreview', 2343535, 'Note', '');");*/
  }

  /**
   * В методе onUpgrade() происходит обновление схемы БД. В данном случае для примера использован
   * примитивный поход с удалением предыдущей базы данных с помощью sql-выражения DROP и последующим
   * ее созданием. Но в реальности если вам будет необходимо сохранить данные, этот метод может
   * включать более сложную логику - добавления новых столбцов, удаление ненужных, добавление
   * дополнительных данных и т.д
   *
   * @param db
   * @param oldVersion
   * @param newVersion
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //   db.execSQL("DROP TABLE IF EXISTS " + "tags");
    onCreate(db);
  }
}
