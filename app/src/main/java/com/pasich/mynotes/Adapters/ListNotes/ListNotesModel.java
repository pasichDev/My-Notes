package com.pasich.mynotes.Adapters.ListNotes;

public class ListNotesModel {
  private final String name;
  private final String date;
  private final boolean folder;
  private final boolean backFolder;

  public ListNotesModel(String name, String date, boolean folder, boolean backFolder) {
    this.name = name;
    this.date = date;
    this.folder = folder;
    this.backFolder = backFolder;
  }

  public boolean getFolder() {
    return this.folder;
  }

  public boolean getBackFolder() {
    return this.backFolder;
  }

  public String getNameList() {
    return this.name;
  }


  public String getDateList() {
    return this.date;
  }

}
