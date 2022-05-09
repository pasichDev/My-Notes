package com.pasich.mynotes.Model.DialogModel;

import static com.pasich.mynotes.Utils.Constants.SystemConstant.folderSystem;

import com.pasich.mynotes.R;

import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyNotesModel {

  private final File[] foldersArray;
  private final String folderOutput;
  public List<String> folderListArray;

  public CopyNotesModel(File dirFile, String folderOutput) {
    this.foldersArray = dirFile.listFiles();
    this.folderListArray = new ArrayList<>();
    this.folderOutput = folderOutput;
    sortArray();
    addRootDirectory();
    createArrayFolders();
  }

  /**
   * Method that creates a list of folders
   */
  private void createArrayFolders() {
    for (File folderSel : foldersArray) {
      if (folderSel.isDirectory()
          && !folderSystem[0].equals(folderSel.getName())
          && !folderSystem[1].equals(folderSel.getName())) {
        folderListArray.add(folderSel.getName());
      }
    }
  }

  /**
   * A method that adds a root folder if needed
   */
  private void addRootDirectory() {
    if (folderOutput.length() >= 1) {
      folderListArray.add(String.valueOf(R.string.rootFolder));
    }
  }

  /**
   * Method that sorts objects by name
   */
  private void sortArray() {
    Arrays.sort(foldersArray, NameFileComparator.NAME_COMPARATOR);
  }
}
