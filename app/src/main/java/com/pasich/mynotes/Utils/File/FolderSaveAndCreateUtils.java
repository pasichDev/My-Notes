package com.pasich.mynotes.Utils.File;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FolderSaveAndCreateUtils {

  private final String path;

  public FolderSaveAndCreateUtils(String path) {
    this.path = path;
  }

  public void renameFolder(String newName, String oldName) {
    try {
      FileUtils.moveDirectory(new File(path + "/" + oldName), new File(path + "/" + newName));
    } catch (final IOException ignored) {

    }
  }

  public void createdFolder(String nameFolder) {
    new File(path + "/" + nameFolder).mkdir();
  }
}
