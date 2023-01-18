package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.di.scope.PerActivity;

public interface BackupContract {

    interface view extends BaseView {

        void initActivity();

        void initConnectAccount();

        void createBackupLocal();

        void createBackupCloud();

        void dialogChoiceVariantAutoBackup();

        void dialogRestoreData(boolean local);

        void restoreFinish(boolean error);
    }


    @PerActivity
    interface presenter extends BasePresenter<view> {

        void loadDataAndEncodeJson(boolean local);

        void restoreDataAndDecodeJson(String jsonRestore);

        String getJsonBackup();

        void openChoiceDialogAutoBackup();

        void loadingDialogRestoreNotes(boolean local);

    }
}
