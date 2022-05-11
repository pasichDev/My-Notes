package com.pasich.mynotes.Utils.File;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TrashUtils {

  /**
   * The file being operated on
   */
  private final File note;
  /**
   * Project root folder
   */
  private final File RootDir;

  /**
   * Folder class
   * @param filesDir - file (File)
   * @param RootDir -
   */
  public TrashUtils(File filesDir, File RootDir) {
    this.note = filesDir;
    this.RootDir = RootDir;
  }

  /**
   * A method that deletes a folder not recursively, but an empty folder
   */
  public void copyFile() {
    try {
      FileUtils.copyFileToDirectory(note, RootDir);
      FileUtils.forceDelete(note);
    } catch (final IOException ignored) {
    }
  }


}
