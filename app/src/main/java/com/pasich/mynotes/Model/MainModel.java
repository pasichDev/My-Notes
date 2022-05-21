package com.pasich.mynotes.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Model.Adapter.NoteItemModel;

import java.util.ArrayList;

public class MainModel extends ModelBase {

  private final Context context;
  public Cursor tags;
  public ArrayList<NoteItemModel> notesArray = new ArrayList<>();

  public MainModel(Context context) {
    super(context);
    this.context = context;
    this.tags = queryTags();
    searchNotes("");
  }

  public void moveToTrash(int position) {
    db.execSQL(
        "INSERT INTO "
            + DbHelper.COLUMN_TRASH
            + "  (title, value, date, type, tag) VALUES ('"
            + notesArray.get(position).getTitle()
            + "','"
            + notesArray.get(position).getValue()
            + "','"
            + notesArray.get(position).getDate()
            + "', 'Note', '"
            + notesArray.get(position).getTags()
            + "');");
    db.execSQL(
        "DELETE FROM "
            + DbHelper.COLUMN_NOTES
            + " WHERE id = '"
            + notesArray.get(position).getId()
            + "';");
  }

  public Cursor queryTags() {
    return db.rawQuery("SELECT * FROM " + DbHelper.COLUMN_TAGS + " ORDER BY name ASC;", null);
  }

  public void createTag(String nameTag) {
    if (nameTag.trim().length() >= 1)
      db.execSQL(
          "INSERT INTO " + DbHelper.COLUMN_TAGS + " (name) VALUES (?);", new String[] {nameTag});
  }

  public void deleteTag(String nameTag, boolean deleteNotes) {
    if (nameTag.trim().length() >= 1) {
      db.execSQL(
          "DELETE FROM " + DbHelper.COLUMN_TAGS + " WHERE name = ?;", new String[] {nameTag});
      if (deleteNotes)
        db.execSQL(
            "DELETE FROM " + DbHelper.COLUMN_NOTES + " WHERE tag = ?;", new String[] {nameTag});
      else {
        ContentValues cv = new ContentValues();
        cv.put("tag", "");
        db.update(DbHelper.COLUMN_NOTES, cv, "tag = ?", new String[] {nameTag});
      }
    }
  }

  public void closeConnection() {
    tags.close();
    db.close();
  }

  public void getUpdateCursor(String tag) {
    notesArray.clear();
    searchNotes(tag);
  }

  public String getSort() {
    String SORT_MODE;
    if (PreferenceManager.getDefaultSharedPreferences(context)
        .getString("sortPref", "date")
        .equals("name")) {
      SORT_MODE = "ORDER BY title ASC";
    } else {
      SORT_MODE = "ORDER BY date DESC";
    }
    return SORT_MODE;
  }

  public void searchNotes(String tag) {
    String where = tag.length() >= 2 ? "WHERE tag = ? " : "";
    Cursor cursorNote =
        db.rawQuery(
            "SELECT * FROM " + DbHelper.COLUMN_NOTES + " " + where + getSort() + ";",
            tag.length() >= 2 ? new String[] {tag} : null);
    while (cursorNote.moveToNext()) {
      notesArray.add(
          new NoteItemModel(
              cursorNote.getInt(0),
              cursorNote.getString(1),
              cursorNote.getString(2),
              cursorNote.getString(3),
              cursorNote.getString(4),
              cursorNote.getString(5)));
    }
    cursorNote.close();
  }
}
