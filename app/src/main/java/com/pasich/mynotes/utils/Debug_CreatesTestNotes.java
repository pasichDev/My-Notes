package com.pasich.mynotes.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Deprecated
public class Debug_CreatesTestNotes {

    private final Context mContext;

    private final File HOME_DIRECTORY;


    public Debug_CreatesTestNotes(Context context) {
        this.mContext = context;
        this.HOME_DIRECTORY = context.getFilesDir();
    }


    public void initTestNotes() {
        ArrayList<String> testNotes = new ArrayList<>();
        testNotes.add("note 1");
        testNotes.add("note 2");
        testNotes.add("note 3");
        testNotes.add("note 4");
        testNotes.add("note 5");
        testNotes.add("note 6");
        testNotes.add("note 7");
        testNotes.add("note 8");
        testNotes.add("note 9");
        testNotes.add("note 10");
        testNotes.add("note 11");
        testNotes.add("note 12");


        for (String text : testNotes) {
            writeNote(new StringBuilder(text), "");
        }
        Log.wtf("My notes (pasichDev)", "count test notes: " + getCountFiles());
    }


    public int getCountFiles() {
        File[] files = HOME_DIRECTORY.listFiles();
        int count = 0;

        assert files != null;
        for (File file : files) {
            count = count + 1;

        }
        return count;
    }


    /**
     * Метод для хранения созданых заметок
     *
     * @param data_file - вместительность заметки
     * @param folder    - папка в которую сохраним заметку
     */
    public void writeNote(StringBuilder data_file, String folder) {
        try {

            String fileName = mContext.getFilesDir() + "/" + folder + data_file + ".txt";

            // отрываем поток для записи
            BufferedWriter bwNote =
                    new BufferedWriter(
                            new FileWriter(mContext.getFilesDir() + "/" + folder + fileName + ".txt"));
            bwNote.write(String.valueOf(data_file));
            bwNote.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
