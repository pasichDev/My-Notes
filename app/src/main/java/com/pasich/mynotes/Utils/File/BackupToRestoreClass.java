package com.pasich.mynotes.Utils.File;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Check.CheckNamesFoldersUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class BackupToRestoreClass {
  private final int BUFFER = 80000;
  private final File HOME_DIRECTORY;
  private final Context context;
  public static final String NAME_FILE_BACKUP = "backupMyNote.zip";

  public BackupToRestoreClass(Context context) {
    this.context = context;
    this.HOME_DIRECTORY = context.getFilesDir();
    deleteOldBackup();
  }

  /** Метод который удаляет прьдедущие бэкапи перед инициализацие класа */
  private void deleteOldBackup() {
    if (new File(HOME_DIRECTORY + "/" + NAME_FILE_BACKUP).exists()) {
      File oldBAckup = new File((HOME_DIRECTORY + "/" + NAME_FILE_BACKUP));
      oldBAckup.delete();
    }
  }

  /** Метод упаковки файлов в zip архив, а именно создание рез. копии */
  public File createBackup() {
    ZipOutputStream out = null;
    File[] files = HOME_DIRECTORY.listFiles();
    int countFIles = 0;
    byte[] data = new byte[BUFFER];

    try {
      out =
          new ZipOutputStream(
              new BufferedOutputStream(
                  new FileOutputStream(HOME_DIRECTORY + "/" + getNameBackup())));
      // Добавляем файлы
      assert files != null;
      for (File file : files) {
        // Добавим файлы но не папки
        if (!file.isDirectory() && file.getName().endsWith(".txt")) {
          processFile(out, data, file.getName());
          countFIles = countFIles + 1;
        }

        // Добавим папки и подпапки
        if (!file.isDirectory() && new CheckNamesFoldersUtils().getMatchFolders(file.getName())) {
          File[] fileDI = new File(HOME_DIRECTORY + "/" + file.getName() + "/").listFiles();
          assert fileDI != null;
          for (File fileNameIsDir : fileDI) {
            if (fileNameIsDir.getName().endsWith(".txt")) {
              processFile(out, data, file.getName() + "/" + fileNameIsDir.getName());
              countFIles = countFIles + 1;
            }
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      close(out);
    }
    return new File(HOME_DIRECTORY + "/" + getNameBackup());
  }

  /**
   * Метод который пакует файлы в zip
   *
   * @param out - адрес zip архива в который пакуем
   * @param data - Размер буфера
   * @param file - файл который добавляем в рахив
   */
  private void processFile(ZipOutputStream out, byte[] data, String file) {
    BufferedInputStream origin = null;
    try {
      origin = new BufferedInputStream(new FileInputStream(HOME_DIRECTORY + "/" + file), BUFFER);
      out.putNextEntry(new ZipEntry(file));
      int count;
      while ((count = origin.read(data, 0, BUFFER)) != -1) {
        out.write(data, 0, count);
      }
    } catch (IOException e) {
      Toast.makeText(context, "IOException" + file, Toast.LENGTH_SHORT).show();
      e.printStackTrace();
    } finally {
      close(origin);
    }
  }

  /**
   * Метод который распаковувает zip архив
   *
   * @param _zipFile - файл который нужно распаковать
   */
  public void unzip(FileDescriptor _zipFile) {
    Log.d("xxx", "Старт распаковки");
    try {
      ZipInputStream zin = new ZipInputStream(new FileInputStream(_zipFile));
      ZipEntry ze;
      int count = 0;
      if (getCountFilles() >= 1) {
        cleanNotes();
      }

      while ((ze = zin.getNextEntry()) != null) {
        Log.d("xxx", "Start file--->" + ze.getName());
        count = count + 1;
        File file = new File(HOME_DIRECTORY + "/" + ze.getName());
        File parent = file.getParentFile();
        assert parent != null;
        if (!parent.exists()) {
          parent.mkdir();
        }

        if (file.getName().endsWith(".txt")) {
          Log.d("xxx", "fileTXT---> " + file.getName());
          FileOutputStream fout = new FileOutputStream(file);
          for (int c = zin.read(); c != -1; c = zin.read()) {
            fout.write(c);
          }

          zin.closeEntry();
          fout.close();
        }
      }
      zin.close();
    } catch (Exception ignored) {

    }
  }

  /**
   * Метод который преносит zip из внутреней памьяти в внешнюю
   *
   * @param source - zip архив
   * @param outputStream - хз
   */
  public void copyFile(@NonNull File source, @NonNull FileOutputStream outputStream)
      throws IOException {
    try (InputStream inputStream = new FileInputStream(source)) {
      int bufferSize = 1024, length;
      byte[] buffer = new byte[bufferSize];
      while ((length = inputStream.read(buffer)) > 0) {
        outputStream.write(buffer, 0, length);
      }

      Toast.makeText(context, R.string.backupIsCreate, Toast.LENGTH_SHORT).show();
      source.delete();
      outputStream.close();
    }
  }

  /** Метод который удаляет все файлы, папки и вместимость папок */
  private void cleanNotes() {
    File[] Files = new File(HOME_DIRECTORY + "/").listFiles();

    assert (Files != null ? Files.length : 0) >= 1;
    for (File file : Files) {
      String fileName = file.getName();
      if (!file.isDirectory() && new CheckNamesFoldersUtils().getMatchFolders(fileName)) {
        File[] dirFiles = new File(HOME_DIRECTORY + "/" + file.getName()).listFiles();
        for (File dFile : dirFiles) {
          dFile.delete();
        }
        file.delete();
      }
      File myFile = new File(HOME_DIRECTORY, fileName);
      myFile.delete();
    }
  }

  /** Метод который возвращает название архива для бэкапа */
  public static String getNameBackup() {
    return NAME_FILE_BACKUP;
  }

  /**
   * Метод который возвращает дату на момент вызова метода
   *
   * @return - формат yyyy-MM-dd
   */
  public static String getDataBackup() {
    return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
  }

  /** Метод который возвращает количество файлов приложения */
  @Deprecated
  public int getCountFilles() {
    /** Метод плохо организован, из-за то что тут не считает файлы в папках и счита пвпки */
    File[] files = HOME_DIRECTORY.listFiles();
    int count = 0;

    assert files != null;
    for (File file : files) {
      if (!new CheckNamesFoldersUtils().getMatchFolders(file.getName())) {
        count = count + 1;
      }
    }
    return count;
  }

  /** Закрытие Streem */
  private void close(Closeable closeable) {
    if (null != closeable) {
      try {
        closeable.close();
      } catch (IOException ignored) {
      }
    }
  }
}
