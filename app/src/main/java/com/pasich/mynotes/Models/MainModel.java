package com.pasich.mynotes.Models;

import static com.pasich.mynotes.Utils.Constants.TagSettingsConst.MAX_NAME_TAG;
import static com.pasich.mynotes.Utils.Constants.TagSettingsConst.MIN_NAME_TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Models.Adapter.NoteItemModel;

import java.util.ArrayList;

public class MainModel extends ModelBase {

  private final Context context;

  /** List of tag names */
  public ArrayList<String> tags = new ArrayList<>();
  /** Note Data List */
  public ArrayList<NoteItemModel> notesArray = new ArrayList<>();

  public MainModel(Context context) {
    super(context);
    this.context = context;
    queryTags();
    searchNotes("");
  }

  /**
   * The method that implements the transfer of notes to the trash
   *
   * @param position - note item position
   */
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

  /** Method that implements filling the list with the names of existing tags */
  @SuppressLint("Recycle")
  public void queryTags() {
    Cursor cursorTag =
        db.rawQuery("SELECT * FROM " + DbHelper.COLUMN_TAGS + " ORDER BY name ASC;", null);
    while (cursorTag.moveToNext()) tags.add(cursorTag.getString(0));
  }

  public boolean createTag(String nameTag) {
    boolean tagCreate = false;
    if (nameTag.trim().length() >= MIN_NAME_TAG && !tags.contains(nameTag)) {
      db.execSQL(
          "INSERT INTO " + DbHelper.COLUMN_TAGS + " (name) VALUES (?);",
          new String[] {setNameTagSize(nameTag)});
      queryTags();
      tagCreate = true;
    }
    return tagCreate;
  }

  private String setNameTagSize(String nameTag) {
    return nameTag.length() > MAX_NAME_TAG ? nameTag.substring(0, MAX_NAME_TAG) : nameTag;
  }

  public void deleteTag(String nameTag, boolean deleteNotes) {
    if (nameTag.trim().length() >= MIN_NAME_TAG) {
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

  public void setNoteTagQuery(int noteID, String nameTag) {
    ContentValues cv = new ContentValues();
    cv.put("tag", setNameTagSize(nameTag));
    db.update(DbHelper.COLUMN_NOTES, cv, "id = ?", new String[] {String.valueOf(noteID)});
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

  public void getUpdateCursor(String tag) {
    notesArray.clear();
    searchNotes(tag);
  }
}
