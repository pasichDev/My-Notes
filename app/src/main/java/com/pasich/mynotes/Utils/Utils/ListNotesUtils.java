package com.pasich.mynotes.Utils.Utils;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

public class ListNotesUtils {

  /**
   * Еhis method returns the modification date of the file
   * @param file - original file
   * @return - date (string)
   */
  public static String returnDateFile(File file) {
    Date lastModDate = new Date(file.lastModified());
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(lastModDate);
  }

  /**
   * This method that sorts an array
   * @param sortPref - key to srt (name,date)
   * @param files - arrayFiles
   */
  public static void sortFileList(String sortPref, File[] files) {
    if (sortPref.equals("name")) {
      Arrays.sort(files, NameFileComparator.NAME_COMPARATOR);
    } else {
      Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
    }
  }
}
