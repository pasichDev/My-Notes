package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.FormattedDataUtil.lastDataCloudBackup;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_AUTO_BACKUP_CLOUD;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;
import static com.pasich.mynotes.utils.constants.Drive_Scope.ACCESS_DRIVE_SCOPE;

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.services.drive.Drive;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.model.backup.JsonBackup;
import com.pasich.mynotes.databinding.ActivityBackupBinding;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.ui.presenter.BackupPresenter;
import com.pasich.mynotes.utils.backup.BackupCacheHelper;
import com.pasich.mynotes.utils.backup.CloudAuthHelper;
import com.pasich.mynotes.utils.backup.CloudCacheHelper;
import com.pasich.mynotes.utils.constants.Cloud_Error;
import com.pasich.mynotes.utils.constants.Drive_Scope;

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
    /**
     * Restore local backup intent
     */
    private final ActivityResultLauncher<Intent> startIntentImport = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            if (result.getData() != null) {
                presenter.readFileBackupLocal(result.getData().getData());
            }
        }
    });
    /**
     * Auth user cloud
     */
    private ActivityResultLauncher<Intent> startAuthIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            cloudAuthHelper.getResultAuth(result.getData()).addOnFailureListener((GoogleSignInAccount) -> onError(R.string.errorAuth, null)).addOnSuccessListener((GoogleSignInAccount) -> {
                cloudCacheHelper.update(GoogleSignInAccount, GoogleSignIn.hasPermissions(GoogleSignInAccount, Drive_Scope.ACCESS_DRIVE_SCOPE), true);
                changeDataUserActivityFromAuth(true);
            });
        }
    });
    /**
     * Save local backup intent
     */
    private ActivityResultLauncher<Intent> startIntentExport = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            if (result.getData() != null) {
                presenter.writeFileBackupLocal(serviceCache, result.getData().getData());
            }
        }
    });
    private Dialog progressDialog;

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
        presenter = null;
        startAuthIntent = null;
        startIntentExport = null;
    }

    @Override
    public void editLastDataEditBackupCloud(long lastDate, boolean error) {
        if (error) {
            showErrors(Cloud_Error.LAST_BACKUP_EMPTY_DRIVE_VIEW);
        } else {
            binding.lastBackupCloud.setText(getString(R.string.lastCloudCopy, lastDataCloudBackup(lastDate)));

        }
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
     * Get Drive Object
     *
     * @return - drive object
     */
    public Drive getDrive() {
        return cloudAuthHelper.getDriveCredentialService(
                cloudCacheHelper.isAuth() ? cloudCacheHelper.getGoogleSignInAccount().getAccount() : null,
                this);
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

            if (presenter.getDataManager().getLastBackupCloudId().equals("null")) {
                loadingLastBackupInfoCloud();
            } else
                editLastDataEditBackupCloud(presenter.getDataManager().getLastDataBackupCloud(), false);

        } else {
            binding.lastBackupCloud.setText(getString(R.string.errorDriverAuthInfo));
            binding.userNameDrive.setText(R.string.errorDriveAuth);
            binding.setIsAuthUser(false);
        }
    }

    /**
     * Save local backup (2/3) - start intent save json file
     *
     * @param jsonValue - appData
     */
    @Override
    public void openIntentSaveBackup(JsonBackup jsonValue) {
        serviceCache.setJsonBackup(jsonValue);
        startIntentExport.launch(new Intent(Intent.ACTION_CREATE_DOCUMENT).addCategory(Intent.CATEGORY_OPENABLE).setType("application/json").putExtra(Intent.EXTRA_TITLE, FILE_NAME_BACKUP));
    }

    /**
     * Restore local backup (2/3) - start intent load json file
     */
    @Override
    public void openIntentReadBackup() {
        startIntentImport.launch(new Intent(Intent.ACTION_OPEN_DOCUMENT).addCategory(Intent.CATEGORY_OPENABLE).setType("application/json"));
    }


    // TODO: 21.01.2023 Установить ограничение на загрузку данных

    /**
     * Loading last backup information (id/date)
     */
    @Override
    public void loadingLastBackupInfoCloud() {
        binding.lastBackupCloud.setText(R.string.checkLastBackupsCloud);
        final Drive mDriveCredential = getDrive();
        final int mError = checkErrorCloud(mDriveCredential);
        if (mError == Cloud_Error.NO_ERROR) {
            presenter.saveDataLoadingLastBackup(mDriveCredential);
        } else if (mError == Cloud_Error.NETWORK_ERROR) {
            showErrors(Cloud_Error.NETWORK_ERROR);
            binding.lastBackupCloud.setText(R.string.errorLoadingLastBackupCloud);
        } else {
            showErrors(mError);
        }
    }

    /**
     * Write backup cloud (2/3)
     */
    @Override
    public void startWriteBackupCloud(JsonBackup jsonBackup) {
        final Drive mDriveCredential = getDrive();
        if (showErrors(checkErrorCloud(mDriveCredential))) {
            presenter.writeFileBackupCloud(mDriveCredential, jsonBackup);
        }
    }

    /**
     * Read backup cloud (2/3)
     */
    @Override
    public void startReadBackupCloud() {
        final Drive mDriveCredential = getDrive();
        final int mError = checkErrorCloud(mDriveCredential);
        if (!showErrors(checkErrorCloud(mDriveCredential))) {
            showErrors(mError);
        } else if (presenter.getDataManager().getLastBackupCloudId().equals("null")) {
            showErrors(Cloud_Error.LAST_BACKUP_EMPTY_RESTORE);
        } else {
            presenter.readFileBackupCloud(mDriveCredential);
        }

    }

    /**
     * Visible progressBar write cloud backup
     */
    @Override
    public void visibleProgressBarCLoud() {
        binding.setIsVisibleProgressCloud(true);
        binding.progressBackupCloud.setProgress(10);
        binding.percentProgress.setText(getString(R.string.percentProgress, 10));
    }

    /**
     * Gone progressBar write cloud backup
     */
    @Override
    public void goneProgressBarCLoud() {
        binding.setIsVisibleProgressCloud(false);
        binding.progressBackupCloud.setProgress(0);
        binding.percentProgress.setText(getString(R.string.percentProgress, 0));
    }

    /**
     * Listener progress uploader file
     *
     * @return - MediaHttpUploaderProgressListener
     */
    @Override
    public MediaHttpUploaderProgressListener getProcessListener() {
        return uploading -> {
            switch (uploading.getUploadState()) {
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
                    runOnUiThread(() -> binding.percentProgress.setText(getString(R.string.percentProgress, 80)));
                    break;
                case MEDIA_COMPLETE:
                    binding.progressBackupCloud.setProgress(99);
                    runOnUiThread(() -> binding.percentProgress.setText(getString(R.string.percentProgress, 99)));
                    break;
            }
        };
    }

    /**
     * Check error from request
     *
     * @param mDriveCredential - check isAuth user
     * @return - code error
     */
    private int checkErrorCloud(@Nullable Drive mDriveCredential) {
        if (!isNetworkConnected()) {
            return Cloud_Error.NETWORK_ERROR;
        }
        if (!cloudCacheHelper.isHasPermissionDrive()) {
            return Cloud_Error.PERMISSION_DRIVE;
        }

        if (!cloudCacheHelper.isAuth()) {
            return Cloud_Error.ERROR_AUTH;
        }
        if (mDriveCredential == null) {
            return Cloud_Error.CREDENTIAL;
        }

        return Cloud_Error.NO_ERROR;
    }


    /**
     * Error processing
     *
     * @param errorCode - code error
     * @return - true - no errors
     */
    @Override
    public boolean showErrors(int errorCode) {
        switch (errorCode) {
            case Cloud_Error.CREDENTIAL:
                onError(R.string.errorCredential, null);
                break;
            case Cloud_Error.PERMISSION_DRIVE:
                if (cloudCacheHelper.isAuth())
                    GoogleSignIn.requestPermissions(this, Drive_Scope.CONST_REQUEST_DRIVE_SCOPE, cloudCacheHelper.getGoogleSignInAccount(), ACCESS_DRIVE_SCOPE);
                else startIntentLogInUserCloud();
                break;
            case Cloud_Error.NETWORK_ERROR:
                onError(R.string.errorConnectedNetwork, null);
                break;
            case Cloud_Error.ERROR_AUTH:
                onError(R.string.errorDriverAuthInfo, null);
                break;
            case Cloud_Error.LAST_BACKUP_EMPTY_DRIVE_VIEW:
                binding.lastBackupCloud.setText(getString(R.string.emptyBackups));
                break;
            case Cloud_Error.LAST_BACKUP_EMPTY_RESTORE:
                onError(R.string.emptyBackups, null);
                break;
            case Cloud_Error.NETWORK_FALSE:
                onError(R.string.errorDriveSync, null);
                break;
            case Cloud_Error.ERROR_CREATE_CLOUD_BACKUP:
                goneProgressBarCLoud();
                onError(R.string.creteLocalCopyFail, null);
                break;
            case Cloud_Error.ERROR_RESTORE_BACKUP:
                onError(R.string.restoreDataFall, null);
                break;
            case Cloud_Error.ERROR_LOAD_LAST_INFO_BACKUP:
                binding.lastBackupCloud.setText(R.string.errorLoadingLastBackupCloud);
                break;
            default:
                return true;
        }
        return false;
    }

    @Override
    public void dialogChoiceVariantAutoBackup() {
        if (showErrors(checkErrorCloud(getDrive()))) {
            new MaterialAlertDialogBuilder(this).setCancelable(true).setTitle(R.string.autoCloudBackupTitle).setSingleChoiceItems(getResources().getStringArray(R.array.autoCloudVariants), presenter.getDataManager().getSetCloudAuthBackup(), (dialog, item) -> {
                editSwitchSetAutoBackup(getResources().getStringArray(R.array.autoCloudVariants)[item]);
                presenter.getDataManager().getBackupCloudInfoPreference().setInt(ARGUMENT_AUTO_BACKUP_CLOUD, getResources().getIntArray(R.array.autoCloudIndexes)[item]);
                dialog.dismiss();

            }).create().show();}
    }

    @Override
    public void dialogRestoreData(boolean local) {
        new MaterialAlertDialogBuilder(this).setCancelable(false).setTitle(R.string.restoreNotesTitle).setMessage(R.string.restoreNotesMessage).setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss()).setPositiveButton(local ? R.string.selectRestore : R.string.nextRestore, (dialog, which) -> {
            if (local) openIntentReadBackup();
            else {
                startReadBackupCloud();
            }
            dialog.dismiss();
        }).create().show();
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
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, R.style.progressDialogRestore).setCancelable(false).setView(R.layout.view_restore_data);
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