package com.pasich.mynotes.otherClasses.models;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_NAME_TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.otherClasses.models.base.ModelBase;

import java.util.ArrayList;

public class MainModel extends ModelBase {

  private final Context context;

  /** List of tag names */
  // public ArrayList<Tag> tagsArray;
  /** Note Data List */
  public ArrayList<Note> notesArray = new ArrayList<>();

  private final ArrayList<String> tagsIgnore = new ArrayList<>();

  public MainModel(Context context) {
    super(context);
    this.context = context;
    // this.tagsArray = queryTags();
    initialization();
  }

  private void initialization() {
    queryTagsIgnore();
    searchNotes("");
    // arraySort();
  }

  /** Method that implements filling the list with the names of existing tags */
  @SuppressLint("Recycle")
  /* public ArrayList<Tag> queryTags() {
   // tagsArray = new ArrayList<>();
    Cursor cursorTag = getDb().query(DbHelper.COLUMN_TAGS, null, null, null, null, null, "name");
    tagsArray.add(new Tag("", 1, false));
    tagsArray.add(new Tag(context.getString(R.string.allNotes), 2, true));
    while (cursorTag.moveToNext()) tagsArray.add(new Tag(cursorTag.getString(0), 0, false));
    return tagsArray;
  }*/

  public void queryTagsIgnore() {
    Cursor cursorTag = getDb().query(DbHelper.COLUMN_TAGS, null, null, null, null, null, "name");
    while (cursorTag.moveToNext())
      if (cursorTag.getInt(1) == 1) tagsIgnore.add(cursorTag.getString(0));
  }

  public boolean createTag(String nameTag) {
    boolean tagCreate = false;
    /* if (nameTag.trim().length() >= MIN_NAME_TAG && !tagsArray.contains(nameTag)) {
      ContentValues cv = new ContentValues();
      cv.put("name", nameTag);
      getDb().insert(DbHelper.COLUMN_TAGS, null, cv);
      tagCreate = true;
      tagsArray.add(new Tag(nameTag, 0, false));
    }*/
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
  /*
  public void arraySort() {
    String sortParam =
        PreferenceManager.getDefaultSharedPreferences(context).getString("sortPref", "DataReserve");

    switch (sortParam) {
      case "DataSort":
        Collections.sort(notesArray, Note.COMPARE_BY_DATE_SORT);
        break;
      case "TitleSort":
        Collections.sort(notesArray, Note.COMPARE_BY_TITLE_SORT);
        break;
      case "TitleReserve":
        Collections.sort(notesArray, Note.COMPARE_BY_TITLE_REVERSE);
        break;
      default:
        Collections.sort(notesArray, Note.COMPARE_BY_DATE_REVERSE);
        break;
    }
  }*/

  public void searchNotes(String tag) {
    tagsIgnore.clear();
    /*  queryTagsIgnore();
    String where = tag.length() >= 2 ? "WHERE tag = ? " : "";
    Cursor cursorNote =
        getDb()
            .rawQuery(
                "SELECT * FROM " + DbHelper.COLUMN_NOTES + " " + where + ";",
                tag.length() >= 2 ? new String[] {tag} : null);
    while (cursorNote.moveToNext()) {
      if (!tagsIgnore.contains(cursorNote.getString(5)) || cursorNote.getString(5).equals(tag))
        notesArray.add(
            new Note(
                cursorNote.getInt(0),
                cursorNote.getString(1),
                cursorNote.getString(2),
                cursorNote.getString(3),
                cursorNote.getString(4),
                cursorNote.getString(5)));
    }
    cursorNote.close();*/
  }

  public void getUpdateCursor(String tag) {
    notesArray.clear();
    searchNotes(tag);
    // arraySort();
  }
}
