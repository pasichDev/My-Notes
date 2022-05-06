package com.pasich.mynotes.Utils.Check;

import android.app.Activity;

import java.io.File;

public class CheckFolderUtils {

  public static final String[] folderCreat = {"trash", "VoiceNotes"};

  /**
   * Method for checking if system folders exist, if they do not exist, the method will create them
   * @param activity
   */
  public static void checkSystemFolder(Activity activity) {
    for (String folder : folderCreat) {
      if (!new File(activity.getFilesDir() + "/" + folder).exists()) {
        new File(activity.getFilesDir() + "/" + folder).mkdir();
      }
    }
  }
}
