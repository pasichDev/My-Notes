package com.pasich.mynotes.Utils.Utils;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

public class ListNotesUtils {

  /**
   * Еhis method returns the modification date of the file
   * @param date - original file date
   * @return - date (string)
   */
  public static String returnDateFile(long date) {
    return new SimpleDateFormat("dd.MM.yyyy  hh:mm", Locale.getDefault()).format(date);
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
