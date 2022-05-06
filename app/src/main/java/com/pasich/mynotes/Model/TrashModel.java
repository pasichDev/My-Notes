package com.pasich.mynotes.Model;

import static com.pasich.mynotes.Utils.Utils.ListNotesUtils.returnDateFile;
import static com.pasich.mynotes.Utils.Utils.ListNotesUtils.sortFileList;

import android.content.Context;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class TrashModel {

  protected final Context context;
  public ArrayList<ListNotesModel> notesArray = new ArrayList<>();
  public final String sortPref;

  public TrashModel(Context context) {
    this.context = context;
    this.sortPref =
        PreferenceManager.getDefaultSharedPreferences(context)
            .getString("sortPref", SystemConstant.Settings_Sort);
    searchNotes();
  }

  /** A method that finds all the notes in the trash folder and appends them to an array */
  public void searchNotes() {
    File dirFiles = new File(context.getFilesDir() + "/trash");
    File[] fileListNames = dirFiles.listFiles((FileFilter) FileFileFilter.FILE);

    assert fileListNames != null;
    if (fileListNames.length >= 1) {
      sortFileList(sortPref, fileListNames);

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
}
