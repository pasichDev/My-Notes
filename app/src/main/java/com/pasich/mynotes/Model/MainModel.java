package com.pasich.mynotes.Model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Model.Adapter.ListNotesModel;

import java.util.ArrayList;

public class MainModel extends ModelBase {

  private final Context context;
  public Cursor tags;
  public ArrayList<ListNotesModel> notesArray = new ArrayList<>();

  public MainModel(Context context) {
    super(context);
    this.context = context;
    this.tags = queryTags();
    searchNotes("");
  }

  /** Method that queries all tags */
  public Cursor queryTags() {
    return db.rawQuery("SELECT * FROM tags ORDER BY name ASC;", null);
  }

  public void createTag(String nameTag) {
    if (nameTag.trim().length() >= 1)
      db.execSQL("INSERT INTO tags (name) VALUES (?);", new String[] {nameTag});
  }

  public void deleteTag(String nameTag) {
    Log.wtf("pasic", nameTag);
    if (nameTag.trim().length() >= 1)
      db.execSQL("DELETE FROM tags WHERE name = '" + nameTag + "';");
  }

  public void closeConnection() {
    tags.close();
    db.close();
  }

  /** Method that clears an array and starts a new one */
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
    String where = tag.length() >= 2 ? "WHERE tag = '" + tag + "' " : "";
    Cursor testCursor = db.rawQuery("SELECT * FROM notes " + where + getSort() + ";", null);
    while (testCursor.moveToNext()) {
      notesArray.add(
          new ListNotesModel(
              testCursor.getInt(0),
              testCursor.getString(1),
              testCursor.getString(2),
              testCursor.getString(5)));
    }
    testCursor.close();
  }
}
