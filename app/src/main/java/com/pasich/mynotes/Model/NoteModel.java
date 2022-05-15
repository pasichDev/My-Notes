package com.pasich.mynotes.Model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pasich.mynotes.Utils.Database.FeedReaderDbHelper;

public class NoteModel {

  protected final Activity activity;
  public boolean newNoteKey;
  public int idKey;
  private final SQLiteDatabase db;
  public Cursor cursorNote;

  public NoteModel(Activity activity) {
    this.activity = activity;
    FeedReaderDbHelper databaseHelper = new FeedReaderDbHelper(activity);
    this.db = databaseHelper.getReadableDatabase();

    loadingKey();
  }

  /** Method that implements getting activity control keys */
  private void loadingKey() {
    newNoteKey = activity.getIntent().getBooleanExtra("NewNote", true);
    idKey = activity.getIntent().getIntExtra("idNote", 0);
  }

  /** Метод который загружает курсор с данними заметки */
  public void queryNote() {
    cursorNote =
        db.rawQuery("SELECT * FROM notes where id = ?", new String[] {String.valueOf(idKey)});
  }
}
