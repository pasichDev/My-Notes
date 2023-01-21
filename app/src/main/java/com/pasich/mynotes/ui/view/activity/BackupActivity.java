package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.FormattedDataUtil.lastDataCloudBackup;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_AUTO_BACKUP_CLOUD;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_TIME;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;
import static com.pasich.mynotes.utils.constants.Drive_Scope.ACCESS_DRIVE_SCOPE;
import static com.pasich.mynotes.utils.constants.Drive_Scope.APPLICATION_NAME;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.Scopes;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.model.JsonBackup;
import com.pasich.mynotes.databinding.ActivityBackupBinding;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.ui.presenter.BackupPresenter;
import com.pasich.mynotes.utils.backup.BackupCacheHelper;
import com.pasich.mynotes.utils.backup.CloudAuthHelper;
import com.pasich.mynotes.utils.backup.CloudCacheHelper;
import com.pasich.mynotes.utils.constants.Drive_Scope;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
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
    @Inject
    public BackupCacheHelper serviceCache;
    @Inject
    public GoogleSignInClient googleSignInClient;
    @Inject
    public CloudCacheHelper cloudCacheHelper;
    @Inject
    public CloudAuthHelper cloudAuthHelper;
    private Dialog progressDialog;

    /**
     * Auth user cloud
     */
    final private ActivityResultLauncher<Intent> startAuthIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    cloudAuthHelper.getResultAuth(result.getData())
                            .addOnFailureListener((GoogleSignInAccount) -> onError(R.string.errorAuth, null))
                            .addOnSuccessListener((GoogleSignInAccount) -> {
                                cloudCacheHelper.update(GoogleSignInAccount, GoogleSignIn.hasPermissions(GoogleSignInAccount, Drive_Scope.ACCESS_DRIVE_SCOPE), true);
                                changeDataUserActivityFromAuth(true);
                            });
                }
            });

    /**
     * Save local backup intent
     */
    private final ActivityResultLauncher<Intent> startIntentExport =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        presenter.writeFileBackupLocal(serviceCache, result.getData().getData());
                    }
                }
            });

    /**
     * Restore local backup intent
     */
    private final ActivityResultLauncher<Intent> openBackupLocalIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            if (result.getData() != null) {
                presenter.readFileBackupLocal(result.getData().getData());
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


        //  OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(AutoBackupCloudWorker.class).build();
        //  WorkManager.getInstance().cancelWorkById(myWorkRequest.getId());
        /// WorkManager.getInstance().enqueue(myWorkRequest);
        // WorkManager.getInstance().cancelAllWork();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Drive_Scope.CONST_REQUEST_DRIVE_SCOPE) {
            cloudCacheHelper.setHasPermissionDrive(resultCode == RESULT_OK);
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
    public void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        editSwitchSetAutoBackup(getResources().getStringArray(R.array.autoCloudVariants)[presenter.getDataManager().getSetCloudAuthBackup()]);
    }

    @Override
    public void initConnectAccount() {
        changeDataUserActivityFromAuth(cloudCacheHelper.isAuth());

    }

    /**
     * Start intent account login
     */
    @Override
    public void startIntentLogInUserCloud() {
        startAuthIntent.launch(googleSignInClient.getSignInIntent());
    }


    /**
     * Edit and update dataUser, from isAuth cloud
     *
     * @param isAuth - check auth cloud
     */
    private void changeDataUserActivityFromAuth(boolean isAuth) {
        if (isAuth) {
            binding.userNameDrive.setText(cloudCacheHelper.getGoogleSignInAccount().getEmail());
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
                GoogleAccountCredential.usingOAuth2(this, Collections.singleton(Scopes.DRIVE_APPFOLDER))
                        .setSelectedAccount(cloudCacheHelper.getGoogleSignInAccount().getAccount()))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    /**
     * Save local backup (2/3) - start intent save json file
     *
     * @param jsonValue - appData
     */
    @Override
    public void openIntentSaveBackup(JsonBackup jsonValue) {
        serviceCache.setJsonBackup(jsonValue);
        startIntentExport.launch(new Intent(Intent.ACTION_CREATE_DOCUMENT)
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setType("application/json")
                .putExtra(Intent.EXTRA_TITLE, FILE_NAME_BACKUP));
    }

    /**
     * Restore local backup (2/3) - start intent load json file
     */
    @Override
    public void openIntentReadBackup() {
        openBackupLocalIntent.launch(new Intent(Intent.ACTION_OPEN_DOCUMENT)
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setType("application/json"));
    }




    //создание файла на експорт и передача на сохранение

    public void createBackupCloud() {
        try {
            final java.io.File copyFileData = new java.io.File(getFilesDir() + FILE_NAME_BACKUP);
            BufferedWriter bwNote =
                    new BufferedWriter(new FileWriter(copyFileData));
            //     bwNote.write(Base64.encodeToString(presenter.getJsonBackup().getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
            bwNote.close();
            writeFileBackupCloud(copyFileData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*


        public Task<java.io.File> createBackupJsonFile(BackupCacheHelper serviceCache) {
            TaskCompletionSource<java.io.File> taskCompletionSource = new TaskCompletionSource<>();

            new Thread(() -> {
                final java.io.File mFile = new java.io.File(mContext.getFilesDir() + FILE_NAME_BACKUP);

          try {
           //     new BufferedWriter(new FileWriter(mFile))
          //              .write(Base64.encodeToString(serviceCache.getJsonBackup().getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
            } catch (IOException e) {
                e.printStackTrace();
            }



                taskCompletionSource.setResult(mFile);
            });
            return taskCompletionSource.getTask();

        }
         */

    }


    //запись данных на клауд
    // TODO: 19.01.2023 Упростить метод, и разбить его на методы
    private void writeFileBackupCloud(java.io.File copyFileData) {


        final Drive mDrive = getDriveCredentialService();
        if (!checkErrorCloud(mDrive)) {
            binding.setIsVisibleProgressCloud(true);
            final ArrayList<String> listIdsDeleted = new ArrayList<>();
            final String oldBackup = presenter.getDataManager().getLastBackupCloudId();

            new Thread(() -> {
                final File fileMetadata = new File();
                final FileContent mediaContent = new FileContent("application/json", copyFileData);
                fileMetadata.setName(FILE_NAME_BACKUP);
                fileMetadata.setParents(Collections.singletonList("appDataFolder"));

                try {
                    if (!oldBackup.equals("null")) {
                        listIdsDeleted.add(mDrive.files().get(oldBackup).getFileId());
                    } else {
                        FileList files = mDrive.files().list()
                                .setSpaces("appDataFolder")
                                .setFields("nextPageToken, files(id, name)")
                                .setPageSize(5)
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

                        for (String idDeleteBackup : listIdsDeleted) {
                            mDrive.files().delete(idDeleteBackup).execute();
                        }
                        presenter.getDataManager().getBackupCloudInfoPreference().setString(ARGUMENT_LAST_BACKUP_ID, file.getId());
                        presenter.getDataManager().getBackupCloudInfoPreference().setLong(ARGUMENT_LAST_BACKUP_TIME, new Date().getTime());
                        runOnUiThread(() -> {
                            editLastDataEditBackupCloud();
                            binding.setIsVisibleProgressCloud(false);
                            binding.percentProgress.setText(getString(R.string.percentProgress, 0));
                            onInfo(R.string.creteLocalCopyOkay, binding.activityBackup);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }
        copyFileData.deleteOnExit();
    }


    //загрузка последних данных
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
                            presenter.getDataManager().getBackupCloudInfoPreference().setString(ARGUMENT_LAST_BACKUP_ID, file.getId());
                            presenter.getDataManager().getBackupCloudInfoPreference().setLong(ARGUMENT_LAST_BACKUP_TIME, file.getModifiedTime().getValue());
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
        if (serviceCache == null) {
            onError(R.string.errorDriverAuthInfo, null);
            return true;
        }

        if (!cloudCacheHelper.isHasPermissionDrive()) {
            GoogleSignIn.requestPermissions(this,
                    Drive_Scope.CONST_REQUEST_DRIVE_SCOPE,
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



    //восстановлени еданных с клауда
    public void loadRestoreBackupCloud() {
        final String idBackupCloud = presenter.getDataManager().getLastBackupCloudId();
        final Drive mDrive = getDriveCredentialService();
        if (!checkErrorCloud(mDrive)) {
            if (!idBackupCloud.equals("null")) {
            /*    runOnUiThread(() -> {
                    progressDialog = processRestoreDialog();
                    progressDialog.show();
                });

             */
                new Thread(() -> {
                    try {
                        OutputStream outputStream = new ByteArrayOutputStream();
                        mDrive.files().get(idBackupCloud)
                                .executeMediaAndDownloadTo(outputStream);
                       /* presenter.restoreDataAndDecodeJson(new String(
                                Base64.decode(outputStream.toString(), Base64.DEFAULT), StandardCharsets.UTF_8));

                        */
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }).start();
            } else {
                onError(R.string.emptyBackups, null);
            }
        }
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

    @Override
    public void dialogRestoreData(boolean local) {
        new MaterialAlertDialogBuilder(this)
                .setCancelable(false)
                .setTitle(R.string.restoreNotesTitle)
                .setMessage(R.string.restoreNotesMessage)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(local ? R.string.selectRestore : R.string.nextRestore, (dialog, which) -> {
                    if (local)
                        openIntentReadBackup();
                    else {
                        presenter.readFileBackupCloud();
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
            onError(R.string.restoreDataFall, null);
        }
    }

    // TODO: 20.01.2023 Нужно проверить как и когда лучше показывать этот диалог, или вообще убрать
    @Override
    public void showProcessRestoreDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, R.style.progressDialogRestore)
                .setCancelable(false)
                .setView(R.layout.view_restore_data);

        progressDialog = builder.create();
        progressDialog.show();
    }

    @Override
    public void emptyDataToBackup() {
        onInfo(R.string.emptyDataToBackup, null);
    }

    @Override
    public void createLocalCopyFinish(boolean error) {
        if (error) {
            onInfo(R.string.creteLocalCopyOkay, null);
        } else {
            onError(R.string.creteLocalCopyFail, null);
        }
    }

    @Override
    public void initListeners() {
    }
}