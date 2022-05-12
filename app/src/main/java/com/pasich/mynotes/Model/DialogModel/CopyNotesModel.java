package com.pasich.mynotes.Model.DialogModel;

import com.pasich.mynotes.Utils.Check.CheckNamesFoldersUtils;

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

  /** Method that creates a list of folders */
  private void createArrayFolders() {
    for (File folderSel : foldersArray) {
      if (folderSel.isDirectory()
          && new CheckNamesFoldersUtils().getMatchFolders(folderSel.getName())) {
        folderListArray.add(folderSel.getName());
      }
    }
  }

  /** A method that adds a root folder if needed */
  private void addRootDirectory() {
    if (folderOutput.length() >= 1) {
      folderListArray.add("...");
    }
  }

  /** Method that sorts objects by name */
  private void sortArray() {
    Arrays.sort(foldersArray, NameFileComparator.NAME_COMPARATOR);
  }
}
