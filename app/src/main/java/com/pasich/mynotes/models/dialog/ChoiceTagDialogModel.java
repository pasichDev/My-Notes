package com.pasich.mynotes.models.dialog;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.pasich.mynotes.models.ModelBase;

public class ChoiceTagDialogModel extends ModelBase {

  private final int switchVisibilityValue;
  private final String tagName;

  public ChoiceTagDialogModel(Context context, String tagName) {
    super(context);
    this.tagName = tagName;
    this.switchVisibilityValue = getValueVisibility();
  }

  @SuppressLint("Recycle")
  private int getValueVisibility() {
    Cursor tagVisibilityCursor =
        getDb()
            .query(
                DbHelper.COLUMN_TAGS,
                new String[] {"visibility"},
                "name = ?",
                new String[] {tagName},
                null,
                null,
                null);
    tagVisibilityCursor.moveToNext();
    return tagVisibilityCursor.getInt(0);
  }

  public int getSwitchVisibilityValue() {
    return switchVisibilityValue;
  }

  public void updateVisibilityTag(boolean valueSwitch) {
    ContentValues cv = new ContentValues();
    cv.put("visibility", valueSwitch ? 1 : 0);
    getDb().update(DbHelper.COLUMN_TAGS, cv, "name = ?", new String[] {tagName});
  }
}
