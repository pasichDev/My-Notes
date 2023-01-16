package com.pasich.mynotes.ui.view.activity;


import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.databinding.ActivityBackupBinding;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.ui.presenter.BackupPresenter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import javax.inject.Inject;

public class BackupActivity extends BaseActivity implements BackupContract.view {

    @Inject
    public BackupContract.presenter presenter;

    @Inject
    public ActivityBackupBinding binding;
    private final Scope ACCESS_DRIVE_SCOPE = new Scope(DriveScopes.DRIVE_APPDATA);
    private final Scope SCOPE_EMAIL = new Scope(Scopes.EMAIL);
    private final ActivityResultLauncher<Intent> startIntentExport =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        ParcelFileDescriptor descriptor;
                        try {
                            descriptor = getContentResolver().openFileDescriptor(result.getData().getData(), "w");
                            FileOutputStream outputStream = new FileOutputStream(descriptor.getFileDescriptor());
                            outputStream.write(presenter.getJsonBackup().getBytes());
                            outputStream.close();
                            descriptor.close();
                            onError(R.string.creteLocalCopyOkay, binding.activityBackup);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        presenter.attachView(this);
        presenter.viewIsReady();
        binding.setPresenter((BackupPresenter) presenter);
    }

    @Override
    public void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.lastBackupCloud.setText(getString(R.string.lastCloudCopy, "22.12.2022"));


        listAppData();
    }


    @Deprecated
    public void listAppData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileList files = getDriveCredential().files().list()
                            .setSpaces("appDataFolder")
                            .setFields("nextPageToken, files(id, name)")
                            .setPageSize(5)
                            .execute();
                    for (File file : files.getFiles()) {
                        Log.wtf("xxx", "Found file: %s (%s)\n" +
                                file.getName() + " / " + file.getId());

                    }
                    Log.wtf("xxx", "checkFunction" + files.size());

                } catch (IOException e) {
                    // TODO(developer) - handle error appropriately
                    System.err.println("Unable to list files: " + e);
                    //   throw e;
                }
            }
        }).start();


    }


    @Override
    public void initConnectAccount() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            binding.userNameDrive.setText(acct.getEmail());
            checkForGooglePermissions();
        } else {

            binding.userNameDrive.setText(R.string.errorDriveAuth);
            binding.cloudExport.setEnabled(false);
            binding.importCloudBackup.setEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public void initListeners() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void createBackupLocal() {
        startIntentExport.launch(new Intent(Intent.ACTION_CREATE_DOCUMENT).addCategory(Intent.CATEGORY_OPENABLE).setType("application/json").putExtra(Intent.EXTRA_TITLE, FILE_NAME_BACKUP));
    }

    @Override
    public void createBackupCloud() {
        try {
            BufferedWriter bwNote =
                    new BufferedWriter(new FileWriter(getFilesDir() + FILE_NAME_BACKUP));
            bwNote.write(String.valueOf(presenter.getJsonBackup()));
            bwNote.close();
            writeFileBackupDisk();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    private void writeFileBackupDisk() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // File's metadata.
                    final File fileMetadata = new File();
                    final java.io.File filePath = new java.io.File(getFilesDir() + FILE_NAME_BACKUP);
                    final FileContent mediaContent = new FileContent("application/json", filePath);
                    fileMetadata.setName(FILE_NAME_BACKUP);
                    fileMetadata.setParents(Collections.singletonList("appDataFolder"));

                    File file = getDriveCredential().files().create(fileMetadata, mediaContent)
                            .setFields("id")
                            .execute();

                    //  return file.getId();
                } catch (GoogleJsonResponseException e) {
                    // TODO(developer) - handle error appropriately
                    System.err.println("Unable to create file: " + e.getDetails());
                    try {
                        throw e;
                    } catch (GoogleJsonResponseException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (IOException e) {
                    if (404 == ((GoogleJsonResponseException) e).getStatusCode()) {
                        // handle  error;
                    }
                    throw new RuntimeException(e);
                }

            }
        }).start();

    }


    /**
     * Метод который возвращает екземпляр Drive, если он не получен возврщает null
     * нужно делать проверку
     *
     * @return
     */
    private Drive getDriveCredential() {
        GoogleSignInAccount mAccount = GoogleSignIn.getLastSignedInAccount(this);

        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        this, Collections.singleton(Scopes.DRIVE_FILE));
        assert mAccount != null;
        credential.setSelectedAccount(mAccount.getAccount());
        return new Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                credential)
                .setApplicationName("My Notes")
                .build();
    }


    /**
     * Проверка на разрешение использовать акаунт Google Drive
     */
    private void checkForGooglePermissions() {
        /**
         * Нужно реализовать
         * если пользователь отклонит разрешение на испольование диск
         * отобразить ошибку и переспросить разрешение
         */
        if (!GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(this),
                ACCESS_DRIVE_SCOPE)) {
            //block start chekc permisson
            GoogleSignIn.requestPermissions(
                    this,
                    22,
                    GoogleSignIn.getLastSignedInAccount(this),
                    ACCESS_DRIVE_SCOPE);
        } else {
            //yes

        }


    }


}