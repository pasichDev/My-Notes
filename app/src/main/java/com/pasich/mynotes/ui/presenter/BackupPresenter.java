package com.pasich.mynotes.ui.presenter;

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
    }


    @Override
    public void openChoiceDialogAutoBackup() {
        getView().dialogChoiceVariantAutoBackup();
    }


    /**
     * Save backup data algorithm and navigator
     *
     * @param local - check repository
     */
    @Override
    public void loadDataAndEncodeJson(boolean local) {
        JsonBackup jsonBackupTemp = new JsonBackup();
        getCompositeDisposable().add(Flowable.zip(getDataManager().getNotes(), getDataManager().getTrashNotesLoad(), getDataManager().getTagsUser(), (noteList, trashNoteList, tagList) -> {
            jsonBackupTemp.setNotes(noteList);
            jsonBackupTemp.setTrashNotes(trashNoteList);
            jsonBackupTemp.setTags(tagList);
            return noteList.size() + trashNoteList.size() + tagList.size();
        }).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(countData -> {
            if (countData != 0) {
                if (local) getView().openIntentSaveBackup(jsonBackupTemp);
                else getView().createBackupCloud();
            } else {
                getView().emptyDataToBackup();
            }
        }));


    }

    /**
     * Restore data algorithm and navigator
     *
     * @param local - check repository
     */
    @Override
    public void loadingDialogRestoreNotes(boolean local) {
        getCompositeDisposable().add(Flowable.zip(getDataManager().getNotes(), getDataManager().getTrashNotesLoad(), getDataManager().getTagsUser(), (noteList, trashNoteList, tagList) -> noteList.size() + trashNoteList.size() + tagList.size()).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(countData -> {
            if (countData == 0) {
                if (local) getView().openIntentReadBackup();
                else {
                    getView().loadRestoreBackupCloud();
                }
            } else {
                getView().dialogRestoreData(local);

            }
        }));


    }

    /**
     * Save data of public file/cloud
     *
     * @param jsonBackup - json public file/cloud
     */
    @Override
    public void restoreData(JsonBackup jsonBackup) {
        getCompositeDisposable().add(Completable.mergeArray(getDataManager().addNotes(jsonBackup.getNotes()), getDataManager().addTags(jsonBackup.getTags()), getDataManager().addTrashNotes(jsonBackup.getTrashNotes())).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(() -> getView().restoreFinish(false)));

    }


}
