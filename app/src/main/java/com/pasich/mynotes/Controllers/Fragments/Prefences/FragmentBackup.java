package com.pasich.mynotes.Controllers.Fragments.Prefences;

import static com.pasich.mynotes.Utils.File.BackupToRestoreClass.getDataBackup;
import static com.pasich.mynotes.Utils.File.BackupToRestoreClass.getNameBackup;
import static com.pasich.mynotes.Utils.Constants.BackConstant.UPDATE_LISTVIEW;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.Dialogs.RestoreNotesDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.BackupToRestoreClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FragmentBackup extends PreferenceFragmentCompat
    implements RestoreNotesDialog.continueImport {

  private BackupToRestoreClass backupClass;

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.backup_prefences, rootKey);
    backupClass = new BackupToRestoreClass(getContext());
    Preference importButton = findPreference("importBackupPreferences");
    Preference exportButton = findPreference("exportBackupPreferences");
    assert importButton != null;
    importButton.setOnPreferenceClickListener(
        preference -> {
          if (backupClass.getCountFilles() == 0) {
            ImportBackup();
          } else {
            // Отобразим диалогове окно перед импортом заметок!
            FragmentManager fm = getParentFragmentManager();
            RestoreNotesDialog RestoreNotes = new RestoreNotesDialog();
            RestoreNotes.setTargetFragment(this, 300);
            RestoreNotes.show(fm, "RestoreFragmentDIalog");
          }
          return true;
        });
    if (backupClass.getCountFilles() >= 1) {
      assert exportButton != null;
      exportButton.setOnPreferenceClickListener(
          preference -> {
            Uri uri = Uri.parse(backupClass.createBackup().toString());
            exportBackup(uri);
            return true;
          });
    } else {
      assert exportButton != null;
      exportButton.setEnabled(false);
    }
  }

  /** Стартуем Импорт и заврешаем начатый старт */
  ActivityResultLauncher<Intent> startIntentImport =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
              Intent data = result.getData();
              if (data != null) {

                try {
                  ParcelFileDescriptor descriptor =
                      getContext()
                          .getApplicationContext()
                          .getContentResolver()
                          .openFileDescriptor(data.getData(), "r");
                  backupClass.unzip(descriptor.getFileDescriptor());
                  Toast.makeText(
                          getContext(),
                          getString(R.string.importNotesFinishSucces),
                          Toast.LENGTH_SHORT)
                      .show();
                  UPDATE_LISTVIEW = true;
                } catch (FileNotFoundException e) {
                  Toast.makeText(getContext(), "FileNotFoundException" + e, Toast.LENGTH_SHORT)
                      .show();
                }
              }
            }
          });

  /** Стартуем експорт и завершаем начатый старт */
  ActivityResultLauncher<Intent> startIntentExport =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
              Intent data = result.getData();
              if (data != null) {
                try {
                  ParcelFileDescriptor descriptor =
                      getContext().getContentResolver().openFileDescriptor(data.getData(), "w");
                  FileOutputStream outputStream =
                      new FileOutputStream(descriptor.getFileDescriptor());
                  backupClass.copyFile(
                      new File(getContext().getFilesDir() + "/" + getNameBackup()), outputStream);
                  Toast.makeText(
                          getContext(), getString(R.string.backupIsCreate), Toast.LENGTH_SHORT)
                      .show();
                } catch (FileNotFoundException e) {
                  Toast.makeText(
                          getContext(),
                          getString(R.string.errorCreateZipRecovery),
                          Toast.LENGTH_SHORT)
                      .show();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            }
          });

  /**
   * Запустим активность для выбора места хранения рез.копии
   *
   * @param pickerInitialUri - ссылка на архив URI
   */
  private void exportBackup(Uri pickerInitialUri) {
    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("application/zip");
    intent.putExtra(Intent.EXTRA_TITLE, getDataBackup() + getNameBackup());
    startIntentExport.launch(intent);
  }

  /** Метод который запускает активность для выбора рез.копии для восстановления */
  private void ImportBackup() {
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("application/zip");
    startIntentImport.launch(intent);
  }

  /** Метод который реализован через интерфейс для вызова из под Диалогового окна */
  @Override
  public void continueImportMethod() {
    ImportBackup();
  }
}
