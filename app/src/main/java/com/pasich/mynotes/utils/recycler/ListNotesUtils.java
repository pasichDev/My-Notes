package com.pasich.mynotes.utils.recycler;


import java.text.SimpleDateFormat;
import java.util.Locale;

public class ListNotesUtils {

    /**
     * This method returns the modification date of the file
     *
     * @param date - original file date
     * @return - date (string)
     */
    public static String convertDate(long date) {
        return new SimpleDateFormat("dd.MM.yyyy  HH:mm", Locale.getDefault()).format(date);
    }

}