package com.pasich.mynotes.Model;

import static com.pasich.mynotes.Utils.Utils.ListNotesUtils.returnDateFile;

import android.content.Context;


import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

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
    File[] fileListNames = dirFiles.listFiles((FileFilter) FileFileFilter.FILE);

    assert fileListNames != null;
    if (fileListNames.length >= 1) {
      Arrays.sort(fileListNames, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
      for (File file : fileListNames) {
        if (file.getName().endsWith(".txt"))
          notesArray.add(new ListNotesModel(file.getName(), returnDateFile(file), false, false));
      }
    }
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
