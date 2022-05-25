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
import java.util.Collections;

public class MainModel extends ModelBase {

  private final Context context;

  /** List of tag names */
  public ArrayList<String> tags = new ArrayList<>();
  /** Note Data List */
  public ArrayList<NoteItemModel> notesArray = new ArrayList<>();

  public MainModel(Context context) {
    super(context);
    this.context = context;
    initialization();
  }

  private void initialization() {
    queryTags();
    searchNotes("");
    arraySort();
  }

  /**
   * The method that implements the transfer of notes to the trash
   *
   * @param noteID - note item position
   */
  public void moveToTrash(int noteID) {
    db.execSQL(
        "INSERT INTO "
            + DbHelper.COLUMN_TRASH
            + " SELECT * FROM "
            + DbHelper.COLUMN_NOTES
            + " WHERE id=?;",
        new String[] {String.valueOf(noteID)});

    db.execSQL("DELETE FROM " + DbHelper.COLUMN_NOTES + " WHERE id = '" + noteID + "';");
  }

  /** Method that implements filling the list with the names of existing tags */
  @SuppressLint("Recycle")
  public void queryTags() {
    Cursor cursorTag = db.query(DbHelper.COLUMN_TAGS, null, null, null, null, null, "name");
    while (cursorTag.moveToNext()) tags.add(cursorTag.getString(0));
  }

  public boolean createTag(String nameTag) {
    boolean tagCreate = false;
    if (nameTag.trim().length() >= MIN_NAME_TAG && !tags.contains(nameTag)) {
      ContentValues cv = new ContentValues();
      cv.put("name", nameTag);
      db.insert(DbHelper.COLUMN_TAGS, null, cv);
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
      db.delete(DbHelper.COLUMN_TAGS, "name = ?", new String[] {nameTag});
      if (deleteNotes) db.delete(DbHelper.COLUMN_NOTES, "tag = ?", new String[] {nameTag});
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

  public void arraySort() {
    String sortParam =
        PreferenceManager.getDefaultSharedPreferences(context).getString("sortPref", "DataReserve");

    switch (sortParam) {
      case "DataSort":
        Collections.sort(notesArray, NoteItemModel.COMPARE_BY_DATE_SORT);
        break;
      case "TitleSort":
        Collections.sort(notesArray, NoteItemModel.COMPARE_BY_TITLE_SORT);
        break;
      case "TitleReserve":
        Collections.sort(notesArray, NoteItemModel.COMPARE_BY_TITLE_REVERSE);
        break;
      default:
        Collections.sort(notesArray, NoteItemModel.COMPARE_BY_DATE_REVERSE);
        break;
    }
  }

  public void searchNotes(String tag) {
    String where = tag.length() >= 2 ? "WHERE tag = ? " : "";
    Cursor cursorNote =
        db.rawQuery(
            "SELECT * FROM " + DbHelper.COLUMN_NOTES + " " + where + ";",
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
    arraySort();
  }
}
