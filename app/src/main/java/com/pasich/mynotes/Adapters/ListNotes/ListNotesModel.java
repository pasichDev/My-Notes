package com.pasich.mynotes.Adapters.ListNotes;

public class ListNotesModel {
  private final String title;
  private final int id;
  private final String date;
  private final String preview;
  private final String tags;

  public ListNotesModel(int id, String title, String preview, String date, String tags) {
    this.id = id;
    this.title = title;
    this.date = date;
    this.tags = tags;
    this.preview = preview;
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
}
