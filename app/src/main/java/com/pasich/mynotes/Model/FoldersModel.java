package com.pasich.mynotes.Model;

import static com.pasich.mynotes.Сore.Methods.ListNotesClass.convertFromFilesArray;
import static com.pasich.mynotes.Сore.Methods.ListNotesClass.returnDateFile;
import android.content.Context;

import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class FoldersModel {

    protected final Context context;
    public ArrayList<ListNotesModel> foldersArray = new ArrayList<>();

    public FoldersModel (Context context){
        this.context = context;
        searchFolders();
    }



    public void searchFolders(){
        File dirFiles = context.getFilesDir();
        File[] folderArray = dirFiles.listFiles();
            if (folderArray.length >= 1) {

                String[] folderName = convertFromFilesArray(Objects.requireNonNull(folderArray));

                for (String file : folderName) {
                    File notesFile = new File(dirFiles, file);
                    if (notesFile.isDirectory()
                            && !notesFile.getName().equals("trash")
                            && !notesFile.getName().equals("VoiceNotes")) {
                        foldersArray.add(new ListNotesModel(file, returnDateFile(notesFile), true, false));
                    }
                }
            }
        }

}
