package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.model.JsonBackup;
import com.pasich.mynotes.di.scope.PerActivity;

public interface BackupContract {

    interface view extends BaseView {

        void initActivity();

        void initConnectAccount();

        void openIntentSaveBackup(JsonBackup jsonBackup);

        void createBackupCloud();

        void dialogChoiceVariantAutoBackup();

        void dialogRestoreData(boolean local);

        void restoreFinish(boolean error);

        void loadRestoreBackupCloud();

        void openIntentReadBackup();

        void emptyDataToBackup();

    }


    @PerActivity
    interface presenter extends BasePresenter<view> {

        void backupData(boolean local);

        void restoreData(JsonBackup jsonRestore);

        void openChoiceDialogAutoBackup();

        void loadingDialogRestoreNotes(boolean local);

    }
}
