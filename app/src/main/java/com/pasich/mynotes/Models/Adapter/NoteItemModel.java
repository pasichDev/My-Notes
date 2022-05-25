package com.pasich.mynotes.Models.Adapter;

import java.util.Comparator;

public class NoteItemModel {
  public static Comparator<NoteItemModel> COMPARE_BY_TITLE_REVERSE =
      (one, other) -> other.getTitle().compareTo(one.getTitle());
  public static Comparator<NoteItemModel> COMPARE_BY_TITLE_SORT =
      (one, other) -> one.getTitle().compareTo(other.getTitle());
  public static Comparator<NoteItemModel> COMPARE_BY_DATE_REVERSE =
      (one, other) -> other.getDate().compareTo(one.getDate());
  public static Comparator<NoteItemModel> COMPARE_BY_DATE_SORT =
      (one, other) -> one.getDate().compareTo(other.getDate());

  private final String title;
  private final int id;
  private final String value;
  private final String date;
  private final String type;
  private String tags;
  private boolean Checked;

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

  public String getDate() {
    return this.date;
  }

  public String setTag(String tag) {
    return this.tags = tag;
  }

  public String getType() {
    return this.type;
  }
}
