package com.pasich.mynotes.base.presenter;

import com.pasich.mynotes.base.view.BaseView;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.api.DriveServiceHelper;
import com.pasich.mynotes.data.api.LocalServiceHelper;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BackupBasePresenter<T extends BaseView> implements com.pasich.mynotes.base.view.BackupPresenter<T> {

    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;
    private final DataManager dataManager;
    private final LocalServiceHelper localServiceHelper;
    private final DriveServiceHelper driveServiceHelper;
    private T view;


    public BackupBasePresenter(SchedulerProvider sh, CompositeDisposable cd, DataManager dt, LocalServiceHelper lc, DriveServiceHelper dsh) {
        this.schedulerProvider = sh;
        this.compositeDisposable = cd;
        this.dataManager = dt;
        this.localServiceHelper = lc;
        this.driveServiceHelper = dsh;
    }


    @Override
    public DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    @Override
    public DriveServiceHelper getDriveServiceHelper() {
        return driveServiceHelper;
    }

    @Override
    public LocalServiceHelper getLocalServiceHelper() {
        return localServiceHelper;
    }

    @Override
    public void attachView(T v) {
        view = v;
    }

    @Override
    public void detachView() {
        compositeDisposable.dispose();
        view = null;
    }

    public T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

}
