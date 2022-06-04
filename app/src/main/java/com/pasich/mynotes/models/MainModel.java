package com.pasich.mynotes.models;

import static com.pasich.mynotes.Utils.Constants.TagSettingsConst.MAX_NAME_TAG;
import static com.pasich.mynotes.Utils.Constants.TagSettingsConst.MIN_NAME_TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.models.adapter.NoteModel;
import com.pasich.mynotes.models.adapter.TagsModel;

import java.util.ArrayList;
import java.util.Collections;

public class MainModel extends ModelBase {

  private final Context context;

  /** List of tag names */
  public ArrayList<TagsModel> tagsArray = new ArrayList<>();
  /** Note Data List */
  public ArrayList<NoteModel> notesArray = new ArrayList<>();

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

  /** Method that implements filling the list with the names of existing tags */
  @SuppressLint("Recycle")
  public void queryTags() {
    Cursor cursorTag = getDb().query(DbHelper.COLUMN_TAGS, null, null, null, null, null, "name");
    tagsArray.add(new TagsModel("", 1, false));
    tagsArray.add(new TagsModel(context.getString(R.string.allNotes), 2, true));
    while (cursorTag.moveToNext()) tagsArray.add(new TagsModel(cursorTag.getString(0), 0, false));
  }

  public boolean createTag(String nameTag) {
    boolean tagCreate = false;
    if (nameTag.trim().length() >= MIN_NAME_TAG && !tagsArray.contains(nameTag)) {
      ContentValues cv = new ContentValues();
      cv.put("name", nameTag);
      getDb().insert(DbHelper.COLUMN_TAGS, null, cv);
      tagCreate = true;
      tagsArray.add(new TagsModel(nameTag, 0, false));
    }
    return tagCreate;
  }

  @SuppressLint("Recycle")
  public int getCountNotesTag(String nameTag) {
    return (int)
        DatabaseUtils.queryNumEntries(
            getDb(), DbHelper.COLUMN_NOTES, "tag = ?", new String[] {String.valueOf(nameTag)});
  }

  private String setNameTagSize(String nameTag) {
    return nameTag.length() > MAX_NAME_TAG ? nameTag.substring(0, MAX_NAME_TAG) : nameTag;
  }

  public void deleteTag(String nameTag, boolean deleteNotes) {
    getDb().delete(DbHelper.COLUMN_TAGS, "name = ?", new String[] {nameTag});
    if (deleteNotes) getDb().delete(DbHelper.COLUMN_NOTES, "tag = ?", new String[] {nameTag});
    else {
      ContentValues cv = new ContentValues();
      cv.put("tag", "");
      getDb().update(DbHelper.COLUMN_NOTES, cv, "tag = ?", new String[] {nameTag});
    }
  }

  public void setNoteTagQuery(int noteID, String nameTag) {
    ContentValues cv = new ContentValues();
    cv.put("tag", setNameTagSize(nameTag));
    getDb().update(DbHelper.COLUMN_NOTES, cv, "id = ?", new String[] {String.valueOf(noteID)});
  }

  public void arraySort() {
    String sortParam =
        PreferenceManager.getDefaultSharedPreferences(context).getString("sortPref", "DataReserve");

    switch (sortParam) {
      case "DataSort":
        Collections.sort(notesArray, NoteModel.COMPARE_BY_DATE_SORT);
        break;
      case "TitleSort":
        Collections.sort(notesArray, NoteModel.COMPARE_BY_TITLE_SORT);
        break;
      case "TitleReserve":
        Collections.sort(notesArray, NoteModel.COMPARE_BY_TITLE_REVERSE);
        break;
      default:
        Collections.sort(notesArray, NoteModel.COMPARE_BY_DATE_REVERSE);
        break;
    }
  }

  public void searchNotes(String tag) {
    String where = tag.length() >= 2 ? "WHERE tag = ? " : "";
    Cursor cursorNote =
        getDb()
            .rawQuery(
                "SELECT * FROM " + DbHelper.COLUMN_NOTES + " " + where + ";",
                tag.length() >= 2 ? new String[] {tag} : null);
    while (cursorNote.moveToNext()) {
      notesArray.add(
          new NoteModel(
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
