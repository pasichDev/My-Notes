package com.pasich.mynotes.ui.view.activity;


import static com.pasich.mynotes.utils.FormattedDataUtil.lastDataCloudBackup;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_AUTO_BACKUP_CLOUD;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_TIME;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.model.DriveConfigTemp;
import com.pasich.mynotes.databinding.ActivityBackupBinding;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.ui.presenter.BackupPresenter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
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
    private Dialog progressDialog;
    public Scope ACCESS_DRIVE_SCOPE = new Scope(DriveScopes.DRIVE_APPDATA);
    public DriveConfigTemp driveConfigTemp;
    private final int CONST_REQUEST_DRIVE_SCOPE = 1245;

    private final ActivityResultLauncher<Intent> startIntentExport =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        writeFileExport(result.getData().getData());
                    }
                }
            });

    private final ActivityResultLauncher<Intent> openBackupLocalIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        Intent data = result.getData();
        if (result.getResultCode() == Activity.RESULT_OK) {
            if (data != null) {
                readJsonBackup(data);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONST_REQUEST_DRIVE_SCOPE) {
            driveConfigTemp.setHasPermissionDrive(resultCode == RESULT_OK);
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
    protected void onDestroy() {
        super.onDestroy();
    }

    private void editLastDataEditBackupCloud() {
        binding.lastBackupCloud.setText(getString(R.string.lastCloudCopy, lastDataCloudBackup(presenter.getDataManager().getLastDataBackupCloud())));
    }

    private void editSwitchSetAutoBackup(String text) {
        binding.switchAutoCloud.setText(text);
    }

    @Override
    public void createBackupLocal() {
        startIntentExport.launch(new Intent(Intent.ACTION_CREATE_DOCUMENT).addCategory(Intent.CATEGORY_OPENABLE).setType("application/json").putExtra(Intent.EXTRA_TITLE, FILE_NAME_BACKUP));
    }

    @Override
    public void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        editSwitchSetAutoBackup(getResources().getStringArray(R.array.autoCloudVariants)[presenter.getDataManager().getSetCloudAuthBackup()]);
    }


    @Override
    public void initConnectAccount() {
        GoogleSignInAccount mLastAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (mLastAccount != null) {
            driveConfigTemp = new DriveConfigTemp(mLastAccount.getAccount(),
                    GoogleSignIn.hasPermissions(
                            GoogleSignIn.getLastSignedInAccount(this),
                            ACCESS_DRIVE_SCOPE));

            binding.userNameDrive.setText(mLastAccount.getEmail());
            binding.setIsAuthUser(true);

            if (presenter.getDataManager().getLastBackupCloudId().equals("null"))
                loadingLastBackupCloudInfo(getDriveCredentialService());
            else editLastDataEditBackupCloud();

        } else {
            binding.lastBackupCloud.setText(getString(R.string.errorDriverAuthInfo));
            binding.userNameDrive.setText(R.string.errorDriveAuth);
            binding.setIsAuthUser(false);
        }
    }


    private Drive getDriveCredentialService() {
        return new Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                GoogleAccountCredential.usingOAuth2(this,
                                Collections.singleton(Scopes.DRIVE_APPFOLDER))
                        .setSelectedAccount(driveConfigTemp.getAccount()))
                .setApplicationName("My Notes")
                .build();
    }


    public void readJsonBackup(Intent data) {
        Runnable runnable = () -> {
            try {
                progressDialog = processRestoreDialog();
                progressDialog.show();
                final ParcelFileDescriptor descriptor = getContentResolver().openFileDescriptor(data.getData(), "r");
                final StringBuilder jsonFile = new StringBuilder();
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(descriptor.getFileDescriptor()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    jsonFile.append(line);
                    jsonFile.append('\n');
                }
                presenter.restoreDataAndDecodeJson(jsonFile.toString());
                descriptor.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 3000);

    }

    private void writeFileExport(Uri uri) {
        try {
            ParcelFileDescriptor descriptor = getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream outputStream = new FileOutputStream(descriptor.getFileDescriptor());
            outputStream.write(presenter.getJsonBackup().getBytes());
            outputStream.close();
            descriptor.close();
            onInfo(R.string.creteLocalCopyOkay, binding.activityBackup);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createBackupCloud() {
        try {
            final java.io.File copyFileData = new java.io.File(getFilesDir() + FILE_NAME_BACKUP);
            BufferedWriter bwNote =
                    new BufferedWriter(new FileWriter(copyFileData));
            bwNote.write(String.valueOf(presenter.getJsonBackup()));
            bwNote.close();
            writeFileBackupCloud(copyFileData);
        } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    private void writeFileBackupCloud(java.io.File copyFileData) {
        final Drive mDrive = getDriveCredentialService();
        if (!checkErrorCloud(mDrive)) {
            binding.setIsVisibleProgressCloud(true);
            final ArrayList<String> listIdsDeleted = new ArrayList<>();
            final String oldBackup = presenter.getDataManager().getLastBackupCloudId();

            new Thread(() -> {
                final File fileMetadata = new File();
                final java.io.File filePath = new java.io.File(getFilesDir() + FILE_NAME_BACKUP);
                final FileContent mediaContent = new FileContent("application/json", filePath);
                fileMetadata.setName(FILE_NAME_BACKUP);
                fileMetadata.setParents(Collections.singletonList("appDataFolder"));

                try {
                    if (!oldBackup.equals("null")) {
                        listIdsDeleted.add(mDrive.files().get(oldBackup).getFileId());
                    } else {
                        FileList files = mDrive.files().list()
                                .setSpaces("appDataFolder")
                                .setFields("nextPageToken, files(id, name)")
                                .setPageSize(10)
                                .execute();
                        for (File file : files.getFiles()) {
                            listIdsDeleted.add(file.getId());
                        }
                    }

                    Drive.Files.Create create = mDrive.files().create(fileMetadata, mediaContent);
                    MediaHttpUploader uploader = create.getMediaHttpUploader();
                    create.setFields("id");
                    uploader.setProgressListener(uploader1 -> {
                        switch (uploader1.getUploadState()) {
                            case INITIATION_STARTED:
                                binding.progressBackupCloud.setProgress(20);
                                runOnUiThread(() -> binding.percentProgress.setText(getString(R.string.percentProgress, 20)));
                                break;
                            case INITIATION_COMPLETE:
                                binding.progressBackupCloud.setProgress(50);
                                runOnUiThread(() -> binding.percentProgress.setText(getString(R.string.percentProgress, 50)));
                                break;
                            case MEDIA_IN_PROGRESS:
                                binding.progressBackupCloud.setProgress(80);
                                runOnUiThread(() -> binding.percentProgress.setText(getString(R.string.percentProgress, 60)));
                                break;
                            case MEDIA_COMPLETE:
                                binding.progressBackupCloud.setProgress(99);
                                runOnUiThread(() -> binding.percentProgress.setText(getString(R.string.percentProgress, 99)));
                                break;
                        }
                    });


                    File file = create.execute();

                    if (file.getId().length() >= 2) {

                        presenter.getDataManager().getBackupCloudInfoPreference()
                                .putString(ARGUMENT_LAST_BACKUP_ID, file.getId())
                                .putLong(ARGUMENT_LAST_BACKUP_TIME, new Date().getTime());
                        runOnUiThread(this::editLastDataEditBackupCloud);
                        //Если есть старые бэкапы удаляем их
                        for (String idDeleteBackup : listIdsDeleted) {
                            mDrive.files().delete(idDeleteBackup).execute();
                        }
                    }
                    runOnUiThread(() -> {
                        binding.setIsVisibleProgressCloud(false);
                        binding.percentProgress.setText(getString(R.string.percentProgress, 0));
                    });
                    runOnUiThread(() -> onInfo(R.string.creteLocalCopyOkay, binding.activityBackup));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }
        copyFileData.deleteOnExit();
    }


    // TODO: 19.01.2023 Обработка исключений, а также улучшение запроса API
    private void loadingLastBackupCloudInfo(Drive mDriveCredential) {
        if (checkErrorCloud(mDriveCredential)) {
            binding.lastBackupCloud.setText(R.string.errorLoadingLastBackupCloud);
        } else {
            new Thread(() -> {
                try {
                    FileList files = mDriveCredential.files().list()
                            .setSpaces("appDataFolder")
                            .setFields("nextPageToken, files(id, name, modifiedTime)")
                            .setOrderBy("modifiedTime")
                            .setPageSize(5)
                            .execute();

                    if (files.size() == 0) {
                        runOnUiThread(() -> binding.lastBackupCloud.setText(getString(R.string.emptyBackups)));
                    } else {
                        for (File file : files.getFiles()) {
                            presenter.getDataManager().getBackupCloudInfoPreference()
                                    .putString(ARGUMENT_LAST_BACKUP_ID, file.getId())
                                    .putLong(ARGUMENT_LAST_BACKUP_TIME, file.getModifiedTime().getValue());
                            runOnUiThread(this::editLastDataEditBackupCloud);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


    private boolean checkErrorCloud(Drive mDriveCredential) {
        if (!isNetworkConnected()) {
            onError(R.string.errorConnectedNetwork, null);
            return true;
        }
        if (driveConfigTemp == null) {
            onError(R.string.errorDriverAuthInfo, null);
            return true;
        }

        if (!driveConfigTemp.isHasPermissionDrive()) {
            GoogleSignIn.requestPermissions(this,
                    CONST_REQUEST_DRIVE_SCOPE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    ACCESS_DRIVE_SCOPE);
            return true;
        }

        if (mDriveCredential == null) {
            onError(R.string.errorCredential, null);
            return true;
        }


        return false;
    }


    @Override
    public void dialogChoiceVariantAutoBackup() {
        new MaterialAlertDialogBuilder(this).setCancelable(true)
                .setTitle(R.string.autoCloudBackupTitle)
                .setSingleChoiceItems(getResources().getStringArray(R.array.autoCloudVariants), presenter.getDataManager().getSetCloudAuthBackup(),
                        (dialog, item) -> {
                            editSwitchSetAutoBackup(getResources().getStringArray(R.array.autoCloudVariants)[item]);
                            presenter.getDataManager()
                                    .getBackupCloudInfoPreference()
                                    .setInt(ARGUMENT_AUTO_BACKUP_CLOUD, getResources().getIntArray(R.array.autoCloudIndexes)[item]);
                            dialog.dismiss();

                        })
                .create().show();
    }


    private void loadRestoreBackupCloud() {


    }


    @Override
    public void dialogRestoreData(boolean local) {
        new MaterialAlertDialogBuilder(this)
                .setCancelable(false)
                .setTitle(R.string.restoreNotesTitle)
                .setMessage(R.string.restoreNotesMessage)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(local ? R.string.selectRestore : R.string.nextRestore, (dialog, which) -> {
                    if (local)
                        openBackupLocalIntent.launch(new Intent(Intent.ACTION_OPEN_DOCUMENT).addCategory(Intent.CATEGORY_OPENABLE).setType("application/json"));
                    else {
                        loadRestoreBackupCloud();
                    }
                    dialog.dismiss();
                })
                .create().show();
    }

    @Override
    public void restoreFinish(boolean error) {

        if (progressDialog != null) progressDialog.dismiss();
        if (!error) {
            onInfo(getString(R.string.restoreDataOkay), null);
        } else {
            onInfo(getString(R.string.restoreDataFall), null);
        }
    }


    public Dialog processRestoreDialog() {
        return new MaterialAlertDialogBuilder(this, R.style.progressDialogRestore)
                .setCancelable(false)
                .setView(R.layout.view_restore_data)
                .create();

    }

    @Override
    public void initListeners() {
    }
}