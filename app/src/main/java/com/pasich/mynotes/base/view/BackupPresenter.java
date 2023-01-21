package com.pasich.mynotes.base.view;

import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.api.DriveServiceHelper;
import com.pasich.mynotes.data.api.LocalServiceHelper;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public interface BackupPresenter<V extends BaseView> {

    void attachView(V mVIew);

    void viewIsReady();

    void detachView();

    DataManager getDataManager();

    CompositeDisposable getCompositeDisposable();

    SchedulerProvider getSchedulerProvider();

    LocalServiceHelper getLocalServiceHelper();

    DriveServiceHelper getDriveServiceHelper();
}
