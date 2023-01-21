package com.pasich.mynotes.ui.presenter;

import android.net.Uri;

import com.pasich.mynotes.base.presenter.BackupBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.api.DriveServiceHelper;
import com.pasich.mynotes.data.api.LocalServiceHelper;
import com.pasich.mynotes.data.model.JsonBackup;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.utils.backup.BackupCacheHelper;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

@PerActivity
public class BackupPresenter extends BackupBasePresenter<BackupContract.view> implements BackupContract.presenter {


    @Inject
    public BackupPresenter(SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable,
                           DataManager dataManager,
                           LocalServiceHelper localServiceHelper,
                           DriveServiceHelper driveServiceHelper) {
        super(schedulerProvider, compositeDisposable, dataManager, localServiceHelper, driveServiceHelper);
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


    // TODO: 20.01.2023 При нажати на иконку диска обновить данные
    // TODO: 20.01.2023 Ограничть количество запросов при ручном обновлении
    @Override
    public void clickInformationCloud(boolean isAuth) {
        if (isAuth) {
            // ручное обновление послдней информации об бэкапе
        } else {
            getView().startIntentLogInUserCloud();
        }
    }

    /**
     * Save backup data algorithm and navigator
     *
     * @param local - check repository
     */
    @Override
    public void saveBackupPresenter(boolean local) {
        JsonBackup jsonBackupTemp = new JsonBackup();
        getCompositeDisposable().add(Flowable.zip(getDataManager().getNotes(), getDataManager().getTrashNotesLoad(), getDataManager().getTagsUser(), (noteList, trashNoteList, tagList) -> {
            jsonBackupTemp.setNotes(noteList);
            jsonBackupTemp.setTrashNotes(trashNoteList);
            jsonBackupTemp.setTags(tagList);
            return noteList.size() + trashNoteList.size() + tagList.size();
        }).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(countData -> {
            if (countData != 0) {
                if (local) getView().openIntentSaveBackup(jsonBackupTemp);
                else writeFileBackupCloud(jsonBackupTemp);
            } else {
                getView().emptyDataToBackup();
            }
        }));
    }


    /**
     * Save local backup (3/3) - write appData to public file
     */
    @Override
    public void writeFileBackupLocal(BackupCacheHelper serviceCache, Uri mUri) {
        getView().createLocalCopyFinish(getLocalServiceHelper().writeBackupLocalFile(serviceCache, mUri));
    }


    /**
     * Restore local backup (3/3) - load public file and write data
     */
    @Override
    public void readFileBackupLocal(Uri mUri) {
        final JsonBackup jsonBackup = getLocalServiceHelper().readBackupLocalFile(mUri);
        if (jsonBackup != null) {
            getView().showProcessRestoreDialog();
            getCompositeDisposable().add(
                    Completable.mergeArray(getDataManager().addNotes(jsonBackup.getNotes()),
                                    getDataManager().addTags(jsonBackup.getTags()),
                                    getDataManager().addTrashNotes(jsonBackup.getTrashNotes()))
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(() -> getView().restoreFinish(false)));
        } else {
            getView().restoreFinish(true);
        }

    }


    // TODO: 20.01.2023 #1 заменить на функцию клауда

    /**
     * Restore data algorithm and navigator
     *
     * @param local - check repository
     */
    @Override
    public void restoreBackupPresenter(boolean local) {
        getCompositeDisposable().add(Flowable.zip(getDataManager().getNotes(), getDataManager().getTrashNotesLoad(), getDataManager().getTagsUser(), (noteList, trashNoteList, tagList) -> noteList.size() + trashNoteList.size() + tagList.size()).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(countData -> {
            if (countData == 0) {
                if (local) getView().openIntentReadBackup();
                else {
                    readFileBackupCloud();
                }
            } else {
                getView().dialogRestoreData(local);

            }
        }));
    }

    @Override
    public void writeFileBackupCloud(JsonBackup jsonBackup) {

    }

    @Override
    public void readFileBackupCloud() {

    }

}
