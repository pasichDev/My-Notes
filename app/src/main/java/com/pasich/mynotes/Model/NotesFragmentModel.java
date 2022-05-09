package com.pasich.mynotes.Model;

import static com.pasich.mynotes.Utils.Constants.SystemConstant.folderSystem;
import static com.pasich.mynotes.Utils.Utils.ListNotesUtils.returnDateFile;
import static com.pasich.mynotes.Utils.Utils.ListNotesUtils.sortFileList;

import android.content.Context;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

import java.io.File;
import java.util.ArrayList;

public class NotesFragmentModel {

  protected final Context context;
  public ArrayList<ListNotesModel> notesArray = new ArrayList<>();

  public NotesFragmentModel(Context context) {
    this.context = context;
    searchNotes();
  }

  /**
   * Method that finds notes and folders in the root directory This note method sorts notes based on
   * the principle of folder at the top.
   */
  public void searchNotes() {
    File[] folderNames = context.getFilesDir().listFiles();
    assert folderNames != null;
    if (folderNames.length >= 1) {
      sortFileList(
          PreferenceManager.getDefaultSharedPreferences(context)
              .getString("sortPref", SystemConstant.Settings_Sort),
          folderNames);

      for (File file : folderNames) {
        if (file.isDirectory()
            && !folderSystem[0].equals(file.getName())
            && !folderSystem[1].equals(file.getName()))
          notesArray.add(new ListNotesModel(file.getName(), returnDateFile(file), true, false));
      }
      for (File file : folderNames) {
        if (file.getName().endsWith(".txt"))
          notesArray.add(new ListNotesModel(file.getName(), returnDateFile(file), false, false));
      }
    }
  }

  /**
   * Method that finds the note inside the selected folder
   *
   * @param folder - select folder
   */
  public void searchNotesForFolder(String folder) {
    File[] folderNames = new File(context.getFilesDir() + "/" + folder).listFiles();
    notesArray.add(new ListNotesModel("...", "", false, true));
    assert folderNames != null;
    if (folderNames.length >= 1) {
      sortFileList(
          PreferenceManager.getDefaultSharedPreferences(context)
              .getString("sortPref", SystemConstant.Settings_Sort),
          folderNames);

      for (File file : folderNames) {
        if (file.getName().endsWith(".txt"))
          notesArray.add(new ListNotesModel(file.getName(), returnDateFile(file), false, false));
      }
    }
  }

  /** Method that clears an array and starts a new one */
  public void getUpdateArray(String folder) {
    notesArray.clear();
    if (folder.length() > 1) searchNotesForFolder(folder);
    else searchNotes();
  }
}
