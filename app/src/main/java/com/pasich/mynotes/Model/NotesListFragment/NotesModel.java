package com.pasich.mynotes.Model.NotesListFragment;

import static com.pasich.mynotes.Utils.Utils.ListNotesUtils.returnDateFile;
import static com.pasich.mynotes.Utils.Utils.ListNotesUtils.sortFileList;

import android.content.Context;
import androidx.preference.PreferenceManager;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

import java.io.File;
import java.util.ArrayList;

public class NotesModel {

  protected final Context context;
  public ArrayList<ListNotesModel> notesArray = new ArrayList<>();

  public NotesModel(Context context) {
    this.context = context;
    searchNotes();
  }

  /**
   * Method that finds notes and folders in the root directory
   * This note method sorts notes based on the principle of folder at the top.
   */
  public void searchNotes() {
    File[] folderNames = context.getFilesDir().listFiles();
    assert folderNames != null;
    if (folderNames.length >= 1) {
      sortFileList(PreferenceManager.getDefaultSharedPreferences(context)
              .getString("sortPref", SystemConstant.Settings_Sort), folderNames);

      for (File file : folderNames) {
        if (file.isDirectory()
            && !file.getName().equals("trash")
            && !file.getName().equals("VoiceNotes"))
          notesArray.add(new ListNotesModel(file.getName(), returnDateFile(file), true, false));
      }
      for (File file : folderNames) {
        if (file.getName().endsWith(".txt"))
          notesArray.add(new ListNotesModel(file.getName(), returnDateFile(file), false, false));
      }
    }
  }


  /**
   * Method that clears an array and starts a new one
   */
  public void getUpdateArray(){
    notesArray.clear();
    searchNotes();
  }
}
