package com.pasich.mynotes.Сore.File;


import static com.pasich.mynotes.Сore.SystemCostant.folderSystem;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.R;

import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileCore extends AppCompatActivity {
    private final Context context;
    private String fileName;

    public FileCore(Context context) {
        this.context = context;
    }

    public int getCountFolder(){
        int countFolder = 0;
        File[] files = new File(context.getFilesDir() + "/").listFiles();
        for (File file : files){
            if(!folderSystem[0].equals(file.getName()) && !folderSystem[1].equals(file.getName()))
            countFolder = file.isDirectory() ? countFolder + 1: countFolder;

        }
        return countFolder;
    }



    //Метод для обрезки расширения файла
    public static String getWithoutExtension(String fileFullPath) {
        if (fileFullPath.endsWith("txt"))
            return fileFullPath.substring(0, fileFullPath.lastIndexOf('.'));
        else
            return fileFullPath;
    }

    public void saveNameFolder(String nameFolder, boolean rename, String oldName) {
        Toast toast = null;
        if (folderSystem[1].equals(nameFolder) || folderSystem[0].equals(nameFolder)) {
            //Блок для названия Trash
            toast = Toast.makeText(context,
                    context.getString(R.string.error_folder_system),
                    Toast.LENGTH_SHORT);
        } else if (nameFolder.trim().length() == 0 || nameFolder == null) {
            //Проверим имя если оно пустое то кик
            toast = Toast.makeText(context,
                    context.getString(R.string.error_name_folder),
                    Toast.LENGTH_SHORT);
        }  else if (!rename) {
            //Если свойтсво сохранить то сохранянем
            File trashDir = new File(context.getFilesDir() + "/" + checkNameFolder(nameFolder));
            trashDir.mkdir();
        } else if (rename) {
            //Если переименовать то перименуем папку
            File oldFolder = new File(context.getFilesDir() + "/" + oldName);
            oldFolder.renameTo(new File(context.getFilesDir() + "/" + checkNameFolder(nameFolder)));
        }
        //Показ Toast
        if (toast != null) {
            toast.show();
        }
    }




    public void deleteFolder(String nameFile) {
        File inputFile = new File(context.getFilesDir() + "/" + nameFile);
        File[] files = inputFile.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        inputFile.delete();
    }

    public void deleteAllNotes() {
        File dirFiles = new File(context.getFilesDir() + "/trash/");
        File[] filex = dirFiles.listFiles((FileFilter) FileFileFilter.FILE);
        assert filex != null;
        String[] files = convertFromFilesArray(filex);
        for (String file : files) {
            File myFile = new File(dirFiles, file);
            myFile.delete();
        }
    }

    private String[] convertFromFilesArray(File[] files) {
        String[] result = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            result[i] = files[i].getName();
        }

        return result;
    }

    //Метод перноса файлов в корзину
    public void transferNotes(String data_file, String folder, String folderOutput) {
        if (new File(context.getFilesDir() + "/" + folder).exists() || folder.equals(context.getString(R.string.rootFolder))) {

            File inputFile;
            if (folderOutput.length() == 0) {
                inputFile = new File(context.getFilesDir() + "/" + data_file);
                inputFile.renameTo(new File(context.getFilesDir() + "/" + folder + "/" + data_file));
            } else {
                inputFile = new File(context.getFilesDir() + "/" + folderOutput + "/" + data_file);
                if (folder.equals(context.getString(R.string.rootFolder))) {
                    inputFile.renameTo(new File(context.getFilesDir() + "/" + data_file));

                } else {
                    inputFile.renameTo(new File(context.getFilesDir() + "/" + folder + "/" + data_file));
                }
            }
            inputFile.delete();

        } else {
            Toast toast = Toast.makeText(context,
                    getString(R.string.error_folder_NoExists),
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Метод перноса файлов из корзины
    public void removeNotesFile(String data_file) {
        File inputFile = new File(context.getFilesDir() + "/trash/" + data_file);
        inputFile.renameTo(new File(context.getFilesDir() + "/" + data_file));
        inputFile.delete();

    }


    /**
     * Метод для хранения созданых заметок
     * @param data_file - вместительность заметки
     * @param folder - папка в которую сохраним заметку
     */
    public void writeNote(StringBuilder data_file, String folder) {
        try {

            //Создадим название папки
            //проверим название на существование если такового файла не существует продолжаем,
            //если существует то добавим индекс
            fileName = checkNameNotes(cleanName(String.valueOf(data_file)), folder);
            //отрываем поток для записи
            BufferedWriter bwNote = new BufferedWriter(new FileWriter(context.getFilesDir() + "/" + folder + fileName + ".txt"));
            bwNote.write(String.valueOf(data_file));
            bwNote.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Метод которые открывает заметки
    //*data file - текст который вводил пользователь
    //*folded - папка из которой было создано заметку
    public StringBuilder readFile(String data_file, String folder) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(context.getFilesDir() + "/" + folder + data_file));
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder;
    }


    /**
     * Метод который сохраняет заметку
     * @param data_text - внутреный текст заметки
     * @param oldName - название старого файла заметки
     * @param folder - папка в которой находиться исходная заметка
     */
    public void editFile(StringBuilder data_text, String oldName, String folder) {
        try {
            //Сделаем проверку на существование такого же файла!
            fileName = cleanName(String.valueOf(data_text)); // здесь строка для сравнения просто имья

            if (oldName.equals(fileName + ".txt")) {
                //Если новое название совпадает старым то мы просто перезапишем файл
                //отрываем поток для записи
                BufferedWriter bwNote = new BufferedWriter(new FileWriter(context.getFilesDir() + "/" + folder + oldName));
                bwNote.write(String.valueOf(data_text));
                bwNote.close();
            } else if (!oldName.equals(fileName + ".txt")) {
                // пишем данные и закрываем поток
                BufferedWriter bwNote = new BufferedWriter(new FileWriter(context.getFilesDir() + "/" + folder + oldName));
                bwNote.write(String.valueOf(data_text));
                bwNote.close();
                fileName = checkNameNotes(fileName, folder);
                //Перейменуем файл
                File oldFile = new File(context.getFilesDir() + "/" + folder + oldName);
                File newFile = new File(context.getFilesDir() + "/" + folder + fileName + ".txt");
                oldFile.renameTo(newFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод который проверяет название папки и добавляет индекс если она существует
     * @param nameFolder - имья которое нужно проверить
     * @return - возвращает проверяное имья папки < 40
     */

    private String checkNameFolder (String nameFolder){
        String name = cleanName(nameFolder);
        File folder = new File(context.getFilesDir() + "/" + nameFolder);
        if(folder.exists() && folder.isDirectory()){
            name = nameFolder + " (1)";
        }
        return name;
    }

    /**
     * Метод который обрезает stringBuilder для названия заметки < 40 символов
     * @param inputName - StringBuilder - который нужно обрезать
     * @return - возвращает название длиной меньнше 40 символов
     */
    public String cleanName(String inputName){
        inputName = inputName.replaceAll("[^\\p{L}\\p{N}\\s]+", "");
        inputName = inputName.trim();
        String nameNoteNoRename;
        if (inputName.length() > 50) {
            nameNoteNoRename = inputName.substring(0, 50);
        } else {
            nameNoteNoRename = inputName;
        }
        //Здесь идет интересный нюанс стирает слеш а остальное оставлаяет
        return nameNoteNoRename.replaceAll("\\n|\\r\\n", " ");
    }

    /**
     * Метод дуже каверкан, желательно переделать в 1.4.x
     * @param nameNotes
     * @param folder
     * @return
     */

    private String checkNameNotes(String nameNotes, String folder) {

       String nameNotesOutput = nameNotes;

            File fileNotes = new File(context.getFilesDir() + "/" + folder + nameNotesOutput + ".txt");
            if (fileNotes.exists()) {
                nameNotesOutput = "(1) " + nameNotesOutput;
            } else if (!fileNotes.exists()) {
            }

        return nameNotesOutput;
    }



}
