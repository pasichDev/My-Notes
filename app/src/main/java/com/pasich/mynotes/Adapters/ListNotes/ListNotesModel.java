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

  /**
   * Requests the value of a folder
   *
   * @return - folders
   */
  public boolean getFolder() {
    return this.folder;
  }

  /**
   * Asks whether this is a link to the root directory
   *
   * @return - root directory
   */
  public boolean getBackFolder() {
    return this.backFolder;
  }

  /**
   * Returns the name of a folder
   *
   * @return - name folder
   */
  public String getNameList() {
    return this.name;
  }

  /**
   * Returns the last modified date
   *
   * @return - last modified date
   */
  public String getDateList() {
    return this.date;
  }
}
