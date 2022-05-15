package com.pasich.mynotes.Adapters.ListNotes;

public class ListNotesModel {
  private final String title;
  private final String date;
  private final String preview;
  private final String tags;

  public ListNotesModel(String title, String preview, String date, String tags) {
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

  public String getPreview() {
    return this.preview;
  }
}
