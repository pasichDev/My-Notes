package com.pasich.mynotes.Adapters.ListNotes;

public class ListNotesModel {
  private String name, date;
  private boolean folder, backFolder;

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

  public void setNameList(String name) {
    this.name = name;
  }

  public String getDateList() {
    return this.date;
  }

  public void setDateList(String date) {
    this.date = date;
  }
}
