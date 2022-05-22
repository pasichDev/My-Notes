package com.pasich.mynotes.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.pasich.mynotes.Model.Adapter.MoreChoiceModel;
import com.pasich.mynotes.R;

import java.util.ArrayList;

public class ChooseTagDialogModel extends ModelBase {

  public final String tagNote;
  private final int noteID;
  public ArrayList<MoreChoiceModel> arrayChoice = new ArrayList<>();
  public String NO_TAG_CONSTANT = "noTag";

  public ChooseTagDialogModel(Context context, int noteID) {
    super(context);
    this.noteID = noteID;
    this.arrayChoice.add(
        new MoreChoiceModel(
            context.getString(R.string.noTag), R.drawable.ic_null, NO_TAG_CONSTANT));
    queryTags();
    this.tagNote = getTagNote();
  }

  @SuppressLint("Recycle")
  public void queryTags() {
    Cursor cursorTags =
        db.rawQuery("SELECT * FROM " + DbHelper.COLUMN_TAGS + " ORDER BY name ASC;", null);
    while (cursorTags.moveToNext()) {
      arrayChoice.add(
          new MoreChoiceModel(cursorTags.getString(0), R.drawable.ic_tag, cursorTags.getString(0)));
    }
  }

  @SuppressLint("Recycle")
  public String getTagNote() {
    Cursor cursorNote =
        db.rawQuery(
            "SELECT tag FROM " + DbHelper.COLUMN_NOTES + " WHERE id = ?;",
            new String[] {String.valueOf(noteID)});
    cursorNote.moveToNext();
    return cursorNote.getString(0);
  }
}
