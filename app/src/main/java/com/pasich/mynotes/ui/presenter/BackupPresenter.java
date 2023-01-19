package com.pasich.mynotes.ui.presenter;


import com.google.gson.Gson;
import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.model.JsonBackup;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
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
                Flowable.zip(getDataManager().getNotes(),
                                getDataManager().getTrashNotesLoad(),
                                getDataManager().getTags(),
                                (noteList, trashNoteList, tagList) -> {
                                    jsonBackupTemp.setNotes(noteList);
                                    jsonBackupTemp.setTrashNotes(trashNoteList);
                                    jsonBackupTemp.setTags(tagList);
                                    return true;
                                })
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(aBoolean -> {
                            setJsonBackup(new Gson().toJson(jsonBackupTemp));
                            if (local)
                                getView().createBackupLocal();
                            else
                                getView().createBackupCloud();
                        }));


    }

    @Override
    public void restoreDataAndDecodeJson(String jsonRestore) {

        if (jsonRestore.length() >= 5) {
            JsonBackup jsonBackupTemp = new Gson().fromJson(jsonRestore, JsonBackup.class);

            getCompositeDisposable()
                    .add(
                            Completable.mergeArray(
                                            getDataManager().addNotes(jsonBackupTemp.getNotes()),
                                            getDataManager().addTags(jsonBackupTemp.getTags()),
                                            getDataManager().addTrashNotes(jsonBackupTemp.getTrashNotes()))
                                    .subscribeOn(getSchedulerProvider().io())
                                    .observeOn(getSchedulerProvider().ui())
                                    .subscribe(() -> getView().restoreFinish(false)));

        } else {
            getView().restoreFinish(true);
        }

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

    @Override
    public void loadingDialogRestoreNotes(boolean local) {
        getView().dialogRestoreData(local);
    }


}
