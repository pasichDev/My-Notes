package com.pasich.mynotes.Model;

import android.app.Activity;
import android.database.Cursor;

import com.pasich.mynotes.Utils.Utils.ListNotesUtils;
import com.pasich.mynotes.View.NoteView;

import java.util.Calendar;

public class NoteModel extends ModelBase {

  protected final Activity activity;
  public boolean newNoteKey;
  public int idKey;
  public String shareText;
  public Cursor cursorNote;
  private final NoteView NoteView;

  public NoteModel(Activity activity, NoteView NoteView) {
    super(activity);
    this.activity = activity;
    this.NoteView = NoteView;

    loadingKey();
  }

  /** Method that implements getting activity control keys */
  private void loadingKey() {
    newNoteKey = activity.getIntent().getBooleanExtra("NewNote", true);
    idKey = activity.getIntent().getIntExtra("idNote", 0);
    shareText = activity.getIntent().getStringExtra("shareText");
  }

  /** Метод который загружает курсор с данними заметки */
  public void queryNote() {
    cursorNote =
        db.rawQuery("SELECT * FROM notes where id = ?", new String[] {String.valueOf(idKey)});
  }

  public void createNote() {
    db.execSQL(
        "INSERT INTO notes  (title, value, date, type, tag) VALUES ('"
            + NoteView.titleName.getText()
            + "','"
            + NoteView.valueNote.getText()
            + "','"
            + ListNotesUtils.returnDateFile(Calendar.getInstance().getTime())
            + "', 'Note', 'test');");
  }

  public void closeConnection() {
    if (cursorNote != null) cursorNote.close();
    db.close();
  }
}
