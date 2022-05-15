package com.pasich.mynotes.Adapters.ListNotes;

public class ListNotesModel {
  private final String name;
  private final String date;
  private final String preview;
  private final String[] tags;

  public ListNotesModel(String name, String date, String[] tags, String preview) {
    this.name = name;
    this.date = date;
    this.tags = tags;
    this.preview = preview;
  }

  public String getNameList() {
    return this.name;
  }

  public String getDateList() {
    return this.date;
  }

  public String[] getTags() {
    return this.tags;
  }

  public String getPreview() {
    return this.preview;
  }
}
