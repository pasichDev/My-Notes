package com.pasich.mynotes.Model.Adapter;

import android.view.View;

public class NoteItemModel {
  private final String title;
  private final int id;
  private final String value;
  private final String tags;
  private final String date;
  private final String type;
  private boolean Checked;
  private View itemView;

  public NoteItemModel(int id, String title, String value, String date, String type, String tags) {
    this.id = id;
    this.title = title;
    this.tags = tags;
    this.value = value;
    this.date = date;
    this.type = type;
    this.Checked = false;
  }

  public String getTitle() {
    return this.title;
  }

  public String getTags() {
    return this.tags;
  }

  public int getId() {
    return this.id;
  }

  public String getValue() {
    return this.value;
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

  public String getDate() {
    return this.date;
  }

  public String getType() {
    return this.type;
  }

  public void setItemView(View v) {
    itemView = v;
  }
}
