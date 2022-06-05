package com.pasich.mynotes.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ListNotesUtils {

  /**
   * Еhis method returns the modification date of the file
   *
   * @param date - original file date
   * @return - date (string)
   */
  public static String returnDateFile(Date date) {
    return new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss", Locale.getDefault()).format(date);
  }

}
