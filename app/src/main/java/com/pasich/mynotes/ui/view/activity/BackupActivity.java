package com.pasich.mynotes.ui.view.activity;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.pasich.mynotes.utils.FormattedDataUtil.lastDataCloudBackup;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_AUTO_BACKUP_CLOUD;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_TIME;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

public class BackupActivity extends BaseActivity implements BackupContract.view {

    @Inject
    public BackupContract.presenter presenter;
    @Inject
    public ActivityBackupBinding binding;
    public GoogleSignInAccount mAcct;
    public Scope ACCESS_DRIVE_SCOPE = new Scope(DriveScopes.DRIVE_APPDATA);

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
                            onInfo(R.string.creteLocalCopyOkay, binding.activityBackup);

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

        onError(R.string.error, null);
    }

    @Override
    public void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        editSwitchSetAutoBackup(getResources().getStringArray(R.array.autoCloudVariants)[presenter.getDataManager().getSetCloudAuthBackup()]);

    }

    @Override
    public void initConnectAccount() {
        mAcct = GoogleSignIn.getLastSignedInAccount(this);
        if (mAcct != null) {
            binding.userNameDrive.setText(mAcct.getEmail());
            checkForGooglePermissions();



            if (!presenter.getDataManager().getLastBackupCloudId().equals("null"))

                editLastDataEditBackupCloud();
            else loadingLastBackupCloudInfo();
        } else {
            binding.lastBackupCloud.setText(getString(R.string.errorDriverAuthInfo));
            binding.userNameDrive.setText(R.string.errorDriveAuth);
            binding.autoBackup.setEnabled(false);
            binding.cloudExport.setEnabled(false);
            binding.importCloudBackup.setEnabled(false);
        }

    }

    /**
     * Метод который изменяет дату посленей модификации рез.копии
     */
    private void editLastDataEditBackupCloud() {
        binding.lastBackupCloud.setText(getString(R.string.lastCloudCopy, lastDataCloudBackup(presenter.getDataManager().getLastDataBackupCloud())));

    }

    private void editSwitchSetAutoBackup(String text) {
        binding.switchAutoCloud.setText(text);

    }


    /**
     * Эта функция реализует поиск последней резервной копии
     * И записываеть информацию в prefences
     */
    private void loadingLastBackupCloudInfo() {
        Log.wtf(TAG, "initConnectAccount: ");
        new Thread(() -> {
            try {
                FileList files = getDriveCredential().files().list()
                        .setSpaces("appDataFolder")
                        .setFields("nextPageToken, files(id, name,modifiedTime)")
                        .setOrderBy("modifiedTime")
                        .setPageSize(5)
                        .execute();
                for (File file : files.getFiles()) {
                    presenter.getDataManager().getBackupCloudInfoPreference().setString(ARGUMENT_LAST_BACKUP_ID, file.getId());
                    presenter.getDataManager().getBackupCloudInfoPreference().setLong(ARGUMENT_LAST_BACKUP_TIME, file.getModifiedTime().getValue());
                    runOnUiThread(this::editLastDataEditBackupCloud);
                }


            } catch (IOException e) {
                System.err.println("Unable to list files: " + e);
            }
        }).start();
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

        //  if (!presenter.getDataManager().getLastBackupCloudId().equals("null")) {
         /*   runOnUiThread(() -> {
                binding.progressBackupCloud.setVisibility(View.VISIBLE);
                binding.lastBackupCloud.setVisibility(View.GONE);
            });

          */
        try {
            BufferedWriter bwNote =
                    new BufferedWriter(new FileWriter(getFilesDir() + FILE_NAME_BACKUP));
            bwNote.write(String.valueOf(presenter.getJsonBackup()));
            bwNote.close();
            writeFileBackupCloud();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



     /*   } else {
            onError(R.string.errorDriveBackup, binding.activityBackup);
        }


      */
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                Log.i("Tag", "active connection");
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                isNetworkConnected();
            }
        });


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected())) {
            //Do something
            return false;
        }
        return true;
    }


    /**
     * Метод который записывает файл рез.копии на GoogleDrive
     */
    private void writeFileBackupCloud() {
        final Drive mDrive = getDriveCredential();
        final ArrayList<String> listIdsDeleted = new ArrayList<>();

        new Thread(() -> {
            final File fileMetadata = new File();
            final java.io.File filePath = new java.io.File(getFilesDir() + FILE_NAME_BACKUP);
            final FileContent mediaContent = new FileContent("application/json", filePath);
            fileMetadata.setName(FILE_NAME_BACKUP);
            fileMetadata.setParents(Collections.singletonList("appDataFolder"));

            try {
                //Ищем старые ненужные бэкапы
                FileList files = mDrive.files().list()
                        .setSpaces("appDataFolder")
                        .setFields("nextPageToken, files(id, name)")
                        .setPageSize(10)
                        .execute();
                for (File file : files.getFiles()) {
                    listIdsDeleted.add(file.getId());
                    Log.wtf(TAG, "writeFileBackupDisk: " + file.getId());
                }

                //Создадим новы бэкап
                File file = mDrive.files().create(fileMetadata, mediaContent)
                        .setFields("id")
                        .execute();
                if (file.getId().length() >= 2) {
                    presenter.getDataManager().getBackupCloudInfoPreference().setString(ARGUMENT_LAST_BACKUP_ID, file.getId());
                    presenter.getDataManager().getBackupCloudInfoPreference().setLong(ARGUMENT_LAST_BACKUP_TIME, new Date().getTime());
                    runOnUiThread(this::editLastDataEditBackupCloud);
                    //Если есть старые бэкапы удаляем их
                    for (String idDeleteBackup : listIdsDeleted) {
                        mDrive.files().delete(idDeleteBackup).execute();
                    }
                }
                new java.io.File(getFilesDir() + FILE_NAME_BACKUP).deleteOnExit();
                runOnUiThread(() -> onInfo(R.string.creteLocalCopyOkay, binding.activityBackup));
            } catch (IOException e) {
                if (404 == ((GoogleJsonResponseException) e).getStatusCode()) {
                    onError(R.string.errorDriveSync, binding.activityBackup);
                }
                throw new RuntimeException(e);
            }

        }).start();

    }


    /**
     * Метод который возвращает екземпляр Drive, если он не получен возврщает null (нужно делать проверку)
     *
     * @return
     */
    private Drive getDriveCredential() {
        return new Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                GoogleAccountCredential.usingOAuth2(this, Collections.singleton(Scopes.DRIVE_FILE)).setSelectedAccount(mAcct.getAccount()))
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


    @Override
    public void dialogChoiceVariantAutoBackup() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.autoCloudBackupTitle)
                .setSingleChoiceItems(getResources().getStringArray(R.array.autoCloudVariants), presenter.getDataManager().getSetCloudAuthBackup(),
                        (dialog, item) -> {
                            editSwitchSetAutoBackup(getResources().getStringArray(R.array.autoCloudVariants)[item]);
                            presenter.getDataManager()
                                    .getBackupCloudInfoPreference()
                                    .setInt(ARGUMENT_AUTO_BACKUP_CLOUD, getResources().getIntArray(R.array.autoCloudIndexes)[item]);
                            dialog.dismiss();

                        });
        builder.create().show();

    }

/*
    public Task<GoogleDriveFileHolder> createFile(String folderId, String filename) {
        return Tasks.call(Executors.newSingleThreadExecutor(), () -> {
            GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();

            List<String> root;
            if (folderId == null) {

                root = Collections.singletonList("root");

            } else {

                root = Collections.singletonList(folderId);
            }
            File metadata = new File()
                    .setParents(root)
                    .setMimeType("text/plain")
                    .setName(filename);

            File googleFile = mDriveService.files().create(metadata).execute();
            if (googleFile == null) {

                throw new IOException("Null result when requesting file creation.");
            }
            googleDriveFileHolder.setId(googleFile.getId());
            return googleDriveFileHolder;
        });
    }


 */


}