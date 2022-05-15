package com.pasich.mynotes.Model;

import android.app.Activity;

public class NoteModel {

  protected final Activity activity;
  public boolean newNoteKey;

  public NoteModel(Activity activity) {
    this.activity = activity;

    loadingKey();
  }

  private void loadingKey() {
    newNoteKey = activity.getIntent().getBooleanExtra("NewNote", true);
  }
}
