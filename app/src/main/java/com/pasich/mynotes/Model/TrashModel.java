package com.pasich.mynotes.Model;

import android.content.Context;

import com.pasich.mynotes.Model.Adapter.ListNotesModel;

import java.io.File;
import java.util.ArrayList;

public class TrashModel {

  protected final Context context;
  public ArrayList<ListNotesModel> notesArray = new ArrayList<>();

  public TrashModel(Context context) {
    this.context = context;
    searchNotes();
  }

  /** A method that finds all the notes in the trash folder and appends them to an array */
  public void searchNotes() {
    File dirFiles = new File(context.getFilesDir() + "/trash");
    //   File[] fileListNames = dirFiles.listFiles((FileFilter) FileFileFilter.FILE);

    //    Arrays.sort(fileListNames, LastModifiedFileComparator.LASTMODIFIED_REVERSE);

    //  if (file.getName().endsWith(".txt"))
    //   notesArray.add(new ListNotesModel(file.getName(), returnDateFile(file.lastModified()),
    // false, false));

  }

  /**
   * Method that returns the size of an array
   *
   * @return - size array
   */
  public int getSizeArray() {
    return notesArray.size();
  }

  /** Method that clears an array and starts a new one */
  public void getUpdateArray() {
    notesArray.clear();
    searchNotes();
  }
}
