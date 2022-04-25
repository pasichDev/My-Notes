package com.pasich.mynotes.Сore.Methods;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

public class ListNotesClass {


    /* **Данный метод возвращает дату изменения файла */
    public static String returnDateFile (File file){
        Date lastModDate = new Date(file.lastModified());
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(lastModDate);
    }

    /* **Данный метод конвертирует массив File[] в String[] */
    public static String[] convertFromFilesArray(File[] files){
        String[] result = new String[files.length];
        for (int i = 0; i<files.length; i++){
            result[i] = files[i].getName(); }
        return result;
    }

    /* **Данный метод сортирует масив заметок    */
    public static void sortFileList(String sortPref, File[] files){
                if(sortPref.equals("name")){
                    Arrays.sort(files, NameFileComparator.NAME_COMPARATOR);
                } else{
                    Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);}
    }





}
