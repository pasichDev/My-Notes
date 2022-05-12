package com.pasich.mynotes.Utils.Check;

import java.io.File;

public class CheckFolderUtils {

  /** Array of folders to check */
  public final String[] folderCreate = {"trash", "VoiceNotes"};

  /**
   * Method for checking if system folders exist, if they do not exist, the method will create them
   */
  public void checkSystemFolder(File file) {
    for (String folder : folderCreate) {
      if (!new File(file + "/" + folder).exists()) new File(file + "/" + folder).mkdir();
    }
  }
}
