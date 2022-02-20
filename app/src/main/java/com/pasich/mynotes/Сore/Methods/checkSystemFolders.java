package com.pasich.mynotes.Сore.Methods;

import android.app.Activity;

import java.io.File;


public class checkSystemFolders {

    public static final String[] folderCreat = {"trash","VoiceNotes"};

    public static void checkSystemFolder(Activity activity) {
        for(String folder : folderCreat){
            if (!new File(activity.getFilesDir() + "/"+folder).exists()) {
                new File(activity.getFilesDir() + "/"+folder).mkdir();
            }
        }
    }

}
