package com.pasich.mynotes.Utils.File.Сount;

import static com.pasich.mynotes.Utils.Constants.SystemConstant.folderSystem;

import java.io.File;
import java.util.Objects;

public class CountUtils {

    /**
     * Method that returns the number of folders in the root directory
     * @param mFile - root folder
     * @return - count folder
     */
    public int getCountFolders(File mFile){
        int countFolder = 0;
        for (File file : Objects.requireNonNull(new File(String.valueOf(mFile)).listFiles())) {
            if (!folderSystem[0].equals(file.getName()) && !folderSystem[1].equals(file.getName()))
                countFolder = file.isDirectory() ? countFolder + 1 : countFolder;
        }
        return countFolder;
    }
}
