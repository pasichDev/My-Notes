package com.pasich.mynotes.otherClasses.models;

import android.content.Context;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.otherClasses.models.base.ModelBase;

import java.util.ArrayList;

public class TrashModel extends ModelBase {

  /** Массив с заметками которые находяться в таблице Trash */
  public ArrayList<Note> notesArray;

  public TrashModel(Context context) {
    super(context);
    this.notesArray = searchNotes();
    // initialization();
  }

  /** Инициализация модели */
  /* private void initialization() {
    Collections.sort(notesArray, Note.COMPARE_BY_DATE_REVERSE);
  }*/

  /**
   * Найдем заметки в таблице Trash, и добавим в массив notesArray
   *
   * @return - возвразаем сформированный массив с заметками
   */
  private ArrayList<Note> searchNotes() {
    notesArray = new ArrayList<>();
    /*  Cursor testCursor = getDb().query(DbHelper.COLUMN_TRASH, null, null, null, null, null, null);
    while (testCursor.moveToNext()) {
      notesArray.add(
          new Note(
              testCursor.getInt(0),
              testCursor.getString(1),
              testCursor.getString(2),
              testCursor.getString(3),
              testCursor.getString(4),
              testCursor.getString(5)));
    }

    testCursor.close();*/
    return notesArray;
  }

  /** Метод который реализовует очистку таблицы Trash */
  public void cleanTrash() {
    getDb().execSQL("DROP TABLE " + DbHelper.COLUMN_TRASH);
    DbHelper.createTableTrash(getDb());
    notesArray.clear();
  }
}
