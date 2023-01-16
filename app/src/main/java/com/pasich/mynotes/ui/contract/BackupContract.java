package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.di.scope.PerActivity;

public interface BackupContract {

    interface view extends BaseView {

        void initActivity();

        void createBackupLocal();

        void createBackupCloud();
    }


    @PerActivity
    interface presenter extends BasePresenter<view> {

        void loadDataAndEncodeJson(boolean local);

        String getJsonBackup();
    }
}
