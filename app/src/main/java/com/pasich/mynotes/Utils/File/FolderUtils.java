package com.pasich.mynotes.Utils.File;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FolderUtils {

  private final File folder;

  public FolderUtils(File filesDir) {
    this.folder = filesDir;
  }

  public void deleteFolder() {
    try {
      FileUtils.deleteDirectory(folder);
    } catch (final IOException ignored) {
    }
  }
  public void deleteFolderForce() {
    try {
      FileUtils.forceDelete(folder);
    } catch (final IOException ignored) {
    }
  }
  public int getNotesForFoldersCount() {
    return Objects.requireNonNull(folder.list()).length;
  }
}
