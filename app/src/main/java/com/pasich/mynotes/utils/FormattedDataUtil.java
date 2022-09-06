package com.pasich.mynotes.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormattedDataUtil {

    /**
     * This method returns the modification date of the file
     *
     * @param date - last modified date
     * @return - formatted date (string)
     */
    public static String convertDateAll(long date) {
        return new SimpleDateFormat("dd.MM.yyyy  HH:mm", Locale.getDefault()).format(date);
    }

    /**
     * This method determines what result to display on the last change day or time
     *
     * @param date - last modified date
     * @return - formatted date
     */

    public static String lastDayEditNote(long date) {
        if (new Date().getTime() - date > 86400)
            return new SimpleDateFormat("d MMM", Locale.getDefault()).format(date);
        else
            return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date);
    }
}