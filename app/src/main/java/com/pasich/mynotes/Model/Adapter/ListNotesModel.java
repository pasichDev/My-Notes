package com.pasich.mynotes.Model.Adapter;

import android.view.View;

public class ListNotesModel {
  private final String title;
  private final int id;
  private final String date;
  private final String preview;
  private final String tags;
  private boolean Checked;
  private View itemView;

  public ListNotesModel(int id, String title, String preview, String date, String tags) {
    this.id = id;
    this.title = title;
    this.date = date;
    this.tags = tags;
    this.preview = preview;
    this.Checked = false;
  }

  public String getTitle() {
    return this.title;
  }

  public String getDate() {
    return this.date;
  }

  public String getTags() {
    return this.tags;
  }

  public int getId() {
    return this.id;
  }

  public String getPreview() {
    return this.preview;
  }

  public boolean getChecked() {
    return this.Checked;
  }

  public void setChecked(boolean arg) {
    this.Checked = arg;
  }

  public View getView() {
    return itemView;
  }

  public void setItemView(View v) {
    itemView = v;
  }
}
