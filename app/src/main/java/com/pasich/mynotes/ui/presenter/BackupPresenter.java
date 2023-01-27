package com.pasich.mynotes.ui.presenter;

import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_TIME;

import android.net.Uri;

import com.google.api.services.drive.Drive;
import com.pasich.mynotes.base.presenter.BasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.model.backup.JsonBackup;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.utils.backup.BackupCacheHelper;
import com.pasich.mynotes.utils.constants.Cloud_Error;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

@ActivityScoped
public class BackupPresenter extends BasePresenter<BackupContract.view> implements BackupContract.presenter {

    private int clickUpdate = 0;

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
     * Click to layout drive info
     *
     * @param isAuth - check auth user
     */
    @Override
    public void clickInformationCloud(boolean isAuth) {
        if (isAuth) {
            if (clickUpdate < 2) {
                getView().loadingLastBackupInfoCloud();
                clickUpdate = clickUpdate + 1;
                if (clickUpdate >= 2) getView().getClickedOffUpdate();
            }
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
        getCompositeDisposable().add(
                Flowable.zip(
                                getDataManager().getNotes(),
                                getDataManager().getTrashNotesLoad(),
                                getDataManager().getTagsUser(),
                                (noteList, trashNoteList, tagList) -> {
                                    jsonBackupTemp.setNotes(noteList);
                                    jsonBackupTemp.setTrashNotes(trashNoteList);
                                    jsonBackupTemp.setTags(tagList);
                                    return noteList.size() + trashNoteList.size() + tagList.size();
                                }).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(countData -> {
                            if (countData != 0) {
                                jsonBackupTemp.setPreferences(getDataManager().getListPreferences());
                                if (local) getView().openIntentSaveBackup(jsonBackupTemp);
                                else getView().startWriteBackupCloud(jsonBackupTemp);
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
        getView().createLocalCopyFinish(getDataManager().writeBackupLocalFile(serviceCache, mUri));
    }


    /**
     * Restore local backup (3/3) - load public file and write data
     */
    @Override
    public void readFileBackupLocal(Uri mUri) {
        getView().showProcessRestoreDialog();
        final JsonBackup jsonBackup = getDataManager().readBackupLocalFile(mUri);
        if (jsonBackup.isError()) {
            getView().restoreFinish(Cloud_Error.BACKUP_DESTROY);
        } else {
            restoreData(jsonBackup);
        }

    }

    /**
     * Restore date request rxJava
     *
     * @param jsonBackup - data restore
     */
    private void restoreData(JsonBackup jsonBackup) {
        getDataManager().setListPreferences(jsonBackup.getPreferences());
        getCompositeDisposable().add(
                Completable.mergeArray(
                                getDataManager().addNotes(jsonBackup.getNotes()),
                                getDataManager().addTags(jsonBackup.getTags()),
                                getDataManager().addTrashNotes(jsonBackup.getTrashNotes()))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .doOnTerminate(() -> getView().restoreFinish(Cloud_Error.OKAY_RESTORE))
                        .subscribe());

    }



    /**
     * Restore data algorithm and navigator
     *
     * @param local - check repository
     */
    @Override
    public void restoreBackupPresenter(boolean local) {
        getDataManager().getCountData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new DisposableSingleObserver<>() {
                    @Override
                    public void onSuccess(Integer countData) {
                        if (countData == 0) {
                            if (local) {
                                getView().openIntentReadBackup();
                            } else {
                                getView().startReadBackupCloud();
                            }
                        } else {
                            getView().dialogRestoreData(local);
                        }

                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    /**
     * Upload backup to cloud (3/3)
     *
     * @param mDriveCredential - check isAuth user
     * @param jsonBackup       - model data backup
     */
    @Override
    public void writeFileBackupCloud(Drive mDriveCredential, JsonBackup jsonBackup) {
        getView().visibleProgressBarCLoud();
        final java.io.File backupTemp = getDataManager().writeTempBackup(jsonBackup);
        if (backupTemp == null) {
            getView().showErrors(Cloud_Error.ERROR_CREATE_CLOUD_BACKUP);
        } else {
            getDataManager()
                    .getOldBackupForCLean(mDriveCredential) //load old backup
                    .onSuccessTask(listBackups ->
                            getDataManager().writeCloudBackup(mDriveCredential,
                                            backupTemp, getView().getProcessListener())//write new backup
                                    .addOnCompleteListener(stack -> {
                                        getView().goneProgressBarCLoud();
                                        backupTemp.delete();
                                    })
                                    .addOnSuccessListener(backupCloud -> {
                                        getView().editLastDataEditBackupCloud(backupCloud.getLastDate(), false);
                                        getDataManager().getBackupCloudInfoPreference().putString(ARGUMENT_LAST_BACKUP_ID, backupCloud.getId()).putLong(ARGUMENT_LAST_BACKUP_TIME, backupCloud.getLastDate());
                                        getView().createLocalCopyFinish(true);

                                    })
                                    .onSuccessTask(backupCloud -> getDataManager().cleanOldBackups(mDriveCredential, listBackups))
                                    .addOnFailureListener(stack -> getView().showErrors(Cloud_Error.NETWORK_FALSE)))

                    .addOnFailureListener(stack -> {
                        backupTemp.delete();
                        getView().goneProgressBarCLoud();
                        getView().showErrors(Cloud_Error.NETWORK_FALSE);
                    });

        }
    }

    /**
     * Load restore backup to cloud (3/3)
     *
     * @param mDriveCredential - check isAuth user
     */
    @Override
    public void readFileBackupCloud(Drive mDriveCredential) {
        getView().showProcessRestoreDialog();
        getDataManager().getReadLastBackupCloud(mDriveCredential)
                .addOnSuccessListener(jsonBackup -> {
                    if (jsonBackup.isError()) {
                        getView().restoreFinish(Cloud_Error.BACKUP_DESTROY);
                    } else {
                        restoreData(jsonBackup);
                    }
                })
                .addOnFailureListener(stack -> getView().restoreFinish(Cloud_Error.NETWORK_ERROR));
    }

    /**
     * Save data last backup cloud
     */
    @Override
    public void saveDataLoadingLastBackup(Drive mDriveCredential) {
        getDataManager().getLastBackupInfo(mDriveCredential)
                .addOnSuccessListener(lastInfo -> {
                    if (lastInfo.getErrorCode() == 0) {
                        getDataManager().getBackupCloudInfoPreference().setString(ARGUMENT_LAST_BACKUP_ID, lastInfo.getId());
                        getDataManager().getBackupCloudInfoPreference().setLong(ARGUMENT_LAST_BACKUP_TIME, lastInfo.getLastDate());
                    } else {
                        getView().showErrors(Cloud_Error.LAST_BACKUP_EMPTY_DRIVE_VIEW);
                    }
                    getView().editLastDataEditBackupCloud(lastInfo.getLastDate(), lastInfo.getErrorCode() != 0);
                }).addOnFailureListener(stack -> getView().showErrors(Cloud_Error.ERROR_LOAD_LAST_INFO_BACKUP));
    }

}
