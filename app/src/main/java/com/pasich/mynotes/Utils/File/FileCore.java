package com.pasich.mynotes.Utils.File;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCore extends AppCompatActivity {
  private final Context context;
  private String fileName;

  public FileCore(Context context) {
    this.context = context;
  }

  /**
   * Метод для хранения созданых заметок
   *
   * @param data_file - вместительность заметки
   * @param folder - папка в которую сохраним заметку
   */
  @Deprecated
  public void writeNote(StringBuilder data_file, String folder) {
    try {

      // Создадим название папки
      // проверим название на существование если такового файла не существует продолжаем,
      // если существует то добавим индекс
      fileName = checkNameNotes(cleanName(String.valueOf(data_file)), folder);
      // отрываем поток для записи
      BufferedWriter bwNote =
          new BufferedWriter(
              new FileWriter(context.getFilesDir() + "/" + folder + fileName + ".txt"));
      bwNote.write(String.valueOf(data_file));
      bwNote.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Метод которые открывает заметки
  // *data file - текст который вводил пользователь
  // *folded - папка из которой было создано заметку
  @Deprecated
  public StringBuilder readFile(String data_file, String folder) {
    StringBuilder stringBuilder = new StringBuilder();
    String line;
    try {
      // открываем поток для чтения
      BufferedReader br =
          new BufferedReader(new FileReader(context.getFilesDir() + "/" + folder + "/"+ data_file));
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
   *
   * @param data_text - внутреный текст заметки
   * @param oldName - название старого файла заметки
   * @param folder - папка в которой находиться исходная заметка
   */
  @Deprecated
  public void editFile(StringBuilder data_text, String oldName, String folder) {
    try {
      // Сделаем проверку на существование такого же файла!
      fileName = cleanName(String.valueOf(data_text)); // здесь строка для сравнения просто имья

      if (oldName.equals(fileName + ".txt")) {
        // Если новое название совпадает старым то мы просто перезапишем файл
        // отрываем поток для записи
        BufferedWriter bwNote =
            new BufferedWriter(new FileWriter(context.getFilesDir() + "/" + folder + oldName));
        bwNote.write(String.valueOf(data_text));
        bwNote.close();
      } else if (!oldName.equals(fileName + ".txt")) {
        // пишем данные и закрываем поток
        BufferedWriter bwNote =
            new BufferedWriter(new FileWriter(context.getFilesDir() + "/" + folder + oldName));
        bwNote.write(String.valueOf(data_text));
        bwNote.close();
        fileName = checkNameNotes(fileName, folder);
        // Перейменуем файл
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
   *
   * @param nameFolder - имья которое нужно проверить
   */
  @Deprecated
  private String checkNameFolder(String nameFolder) {
    String name = cleanName(nameFolder);
    File folder = new File(context.getFilesDir() + "/" + nameFolder);
    if (folder.exists() && folder.isDirectory()) {
      name = nameFolder + " (1)";
    }
    return name;
  }

  /**
   * Метод который обрезает stringBuilder для названия заметки < 40 символов
   *
   * @param inputName - StringBuilder - который нужно обрезать
   * @return - возвращает название длиной меньнше 40 символов
   */
  @Deprecated
  public String cleanName(String inputName) {
    inputName = inputName.replaceAll("[^\\p{L}\\p{N}\\s]+", "");
    inputName = inputName.trim();
    String nameNoteNoRename;
    if (inputName.length() > 50) {
      nameNoteNoRename = inputName.substring(0, 50);
    } else {
      nameNoteNoRename = inputName;
    }
    // Здесь идет интересный нюанс стирает слеш а остальное оставлаяет
    return nameNoteNoRename.replaceAll("\\n|\\r\\n", " ");
  }

  /**
   * Метод дуже каверкан, желательно переделать в 1.4.x
   *
   * @param nameNotes
   * @param folder
   * @return
   */
  @Deprecated
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
