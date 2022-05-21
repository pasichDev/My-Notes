package com.pasich.mynotes.Model;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;

import com.pasich.mynotes.Utils.Utils.ListNotesUtils;
import com.pasich.mynotes.View.NoteView;

import java.util.Calendar;

public class NoteModel extends ModelBase {

  protected final Activity activity;
  private final NoteView NoteView;
  public boolean newNoteKey;
  public int idKey;
  public String shareText, tagNote;
  public Cursor cursorNote;

  public NoteModel(Activity activity, NoteView NoteView) {
    super(activity);
    this.activity = activity;
    this.NoteView = NoteView;

    loadingKey();
  }

  /** Method that implements getting activity control keys */
  private void loadingKey() {
    this.newNoteKey = activity.getIntent().getBooleanExtra("NewNote", true);
    this.idKey = activity.getIntent().getIntExtra("idNote", 0);
    this.tagNote = activity.getIntent().getStringExtra("tagNote");
    this.shareText = activity.getIntent().getStringExtra("shareText");
  }

  /** Метод который загружает курсор с данними заметки */
  public void queryNote() {
    cursorNote =
        db.rawQuery(
            "SELECT * FROM " + DbHelper.COLUMN_NOTES + " where id = ?",
            new String[] {String.valueOf(idKey)});
  }

  public void createNote() {
    Log.wtf("pasich", tagNote);
    db.execSQL(
        "INSERT INTO "
            + DbHelper.COLUMN_NOTES
            + "  (title, value, date, type, tag) VALUES ('"
            + NoteView.titleName.getText()
            + "','"
            + NoteView.valueNote.getText()
            + "','"
            + ListNotesUtils.returnDateFile(Calendar.getInstance().getTime())
            + "', 'Note', '"
            + this.tagNote
            + "');");
  }

  public void closeConnection() {
    if (cursorNote != null) cursorNote.close();
    db.close();
  }
}
