package com.pasich.mynotes.Сore.ListContolers;

import static com.pasich.mynotes.Сore.Methods.ListNotesClass.convertFromFilesArray;
import static com.pasich.mynotes.Сore.Methods.ListNotesClass.returnDateFile;
import static com.pasich.mynotes.Сore.Methods.ListNotesClass.sortFileList;

import android.content.Context;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.ListNotes.ListNotesfor;
import com.pasich.mynotes.Сore.SystemCostant;

import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Objects;

public class NotesListData {

  private final Context context;

  public NotesListData(Context context) {
    this.context = context;
  }

  public ArrayList newListAdapter(String folder, boolean mode_folder) {
    ArrayList listNotesfors = new ArrayList();
    String sortPref =
        PreferenceManager.getDefaultSharedPreferences(context)
            .getString("sortPref", SystemCostant.Settings_Sort);

    File dirFiles;
    if (!folder.equals("")) {
      dirFiles = new File(context.getFilesDir() + "/" + folder);
    } else {
      dirFiles = context.getFilesDir();
    }

    if (mode_folder) {
      File[] folderNames = dirFiles.listFiles();
      if (folderNames.length >= 1) {
        sortFileList(sortPref, folderNames);
        String[] folderName = convertFromFilesArray(Objects.requireNonNull(folderNames));

        for (String file : folderName) {
          // Узнаем дату
          File notesFile = new File(dirFiles, file);
          if (notesFile.isDirectory()
              && !notesFile.getName().equals("trash")
              && !notesFile.getName().equals("VoiceNotes")) {
            listNotesfors.add(new ListNotesfor(file, returnDateFile(notesFile), true, false));
          }
        }
      }
    }

    if (!folder.equals("")) {
      listNotesfors.add(new ListNotesfor("...", "", false, true));
    }

    // Список файлов!
    File[] fileNames = dirFiles.listFiles((FileFilter) FileFileFilter.FILE);
    if (fileNames.length >= 1) {
      sortFileList(sortPref, fileNames);
      String[] fileName = convertFromFilesArray(Objects.requireNonNull(fileNames));

      for (String file : fileName) {
        // Узнаем дату
        File notesFile = new File(dirFiles, file);
        if (file.endsWith(".txt")) {
          listNotesfors.add(new ListNotesfor(file, returnDateFile(notesFile), false, false));
        }
      }
    }
    return listNotesfors;
  }
}
