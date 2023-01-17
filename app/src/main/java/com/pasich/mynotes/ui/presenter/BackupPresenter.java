package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.model.JsonBackup;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@PerActivity
public class BackupPresenter extends AppBasePresenter<BackupContract.view> implements BackupContract.presenter {


    private String jsonBackup;


    @Inject
    public BackupPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().initActivity();
        getView().initConnectAccount();
        getView().initListeners();
    }

    @Override
    public void detachView() {
        super.detachView();
        jsonBackup = null;
    }

    @Override
    public void loadDataAndEncodeJson(boolean local) {
        JsonBackup jsonBackupTemp = new JsonBackup();

        getCompositeDisposable().add(
                getDataManager().getNotes()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(jsonBackupTemp::setNotes));

     /*   setJsonBackup(new Gson().toJson(noteList));
        if (local)
            getView().createBackupLocal();
        else
            getView().createBackupCloud();

      */
    }

    public void setJsonBackup(String jsonBackup) {
        this.jsonBackup = jsonBackup;
    }

    public String getJsonBackup() {
        return jsonBackup;
    }

    @Override
    public void openChoiceDialogAutoBackup() {
        getView().dialogChoiceVariantAutoBackup();
    }
}
