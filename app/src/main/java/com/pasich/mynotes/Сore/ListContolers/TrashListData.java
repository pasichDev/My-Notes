package com.pasich.mynotes.Сore.ListContolers;

import static com.pasich.mynotes.Сore.Methods.ListNotesClass.convertFromFilesArray;
import static com.pasich.mynotes.Сore.Methods.ListNotesClass.returnDateFile;
import static com.pasich.mynotes.Сore.Methods.ListNotesClass.sortFileList;

import android.content.Context;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Сore.SystemCostant;

import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Objects;

public class TrashListData {

  private final Context context;
  protected String sortPref;

  public TrashListData(Context context) {
    this.context = context;
  }

  public ArrayList newListAdapter() {
    ArrayList listNotesfors = new ArrayList();
    sortPref =
        PreferenceManager.getDefaultSharedPreferences(context)
            .getString("sortPref", SystemCostant.Settings_Sort);

    File dirFiles = new File(context.getFilesDir() + "/trash");
    // Список файлов!
    File[] fileNames = dirFiles.listFiles((FileFilter) FileFileFilter.FILE);
    if (fileNames.length >= 1) {
      sortFileList(sortPref, fileNames);
      String[] fileName = convertFromFilesArray(Objects.requireNonNull(fileNames));

      for (String file : fileName) {
        File notesFile = new File(dirFiles, file);
        if (file.endsWith(".txt")) {
          listNotesfors.add(new ListNotesModel(file, returnDateFile(notesFile), false, false));
        }
      }
    }
    return listNotesfors;
  }
}
