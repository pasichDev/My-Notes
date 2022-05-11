package com.pasich.mynotes.Utils.File;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FolderUtils {

  private final File folder;

  /**
   * Folder class
   * @param filesDir - folder (File)
   */
  public FolderUtils(File filesDir) {
    this.folder = filesDir;
  }

  /**
   * A method that deletes a folder not recursively, but an empty folder
   */
  public void deleteFolder() {
    try {
      FileUtils.deleteDirectory(folder);
    } catch (final IOException ignored) {
    }
  }

  /**
   * Method that deletes a full folder and all its capacity
   */
  public void deleteFolderForce() {
    try {
      FileUtils.forceDelete(folder);
    } catch (final IOException ignored) {
    }
  }

  /**
   * Method that clears a folder from android java files
   */
  public void cleanFolder() {
    try {
      FileUtils.cleanDirectory(folder);
    } catch (final IOException ignored) {
    }
  }
  /**
   *
   * @return - Number of files in a folder
   */
  public int getNotesForFoldersCount() {
    return Objects.requireNonNull(folder.list()).length;
  }
}
