package com.pasich.mynotes.ui.contract;


import android.net.Uri;

import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.services.drive.Drive;
import com.pasich.mynotes.base.view.BackupPresenter;
import com.pasich.mynotes.base.view.BaseView;
import com.pasich.mynotes.data.model.backup.JsonBackup;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.utils.backup.BackupCacheHelper;

public interface BackupContract {

    interface view extends BaseView {

        void initActivity();

        void initConnectAccount();

        void startIntentLogInUserCloud();

        void loadingLastBackupInfoCloud();

        void openIntentSaveBackup(JsonBackup jsonBackup);

        void openIntentReadBackup();

        void dialogChoiceVariantAutoBackup();

        void dialogRestoreData(boolean local);

        void restoreFinish(boolean error);

        void showProcessRestoreDialog();

        void emptyDataToBackup();

        void createLocalCopyFinish(boolean error);

        boolean showErrors(int errorCode);

        void editLastDataEditBackupCloud(long lastDate, boolean error);

        void startWriteBackupCloud(JsonBackup jsonBackup);

        void startReadBackupCloud();

        void visibleProgressBarCLoud();

        void goneProgressBarCLoud();

        void getClickedOffUpdate();

        MediaHttpUploaderProgressListener getProcessListener();

    }


    @PerActivity
    interface presenter extends BackupPresenter<view> {

        void clickInformationCloud(boolean isAuth);

        void saveBackupPresenter(boolean local);

        void restoreBackupPresenter(boolean local);

        void writeFileBackupLocal(BackupCacheHelper serviceCache, Uri mUri);

        void openChoiceDialogAutoBackup();

        void readFileBackupLocal(Uri mUri);

        void writeFileBackupCloud(Drive mDriveCredential, JsonBackup jsonBackup);

        void readFileBackupCloud(Drive mDriveCredential);

        void saveDataLoadingLastBackup(Drive mDriveCredential);
    }
}
