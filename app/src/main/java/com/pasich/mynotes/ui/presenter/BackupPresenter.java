package com.pasich.mynotes.ui.presenter;

import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_TIME;

import android.net.Uri;

import com.google.api.services.drive.Drive;
import com.pasich.mynotes.base.presenter.BackupBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.api.DriveServiceHelper;
import com.pasich.mynotes.data.api.LocalServiceHelper;
import com.pasich.mynotes.data.model.JsonBackup;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.utils.backup.BackupCacheHelper;
import com.pasich.mynotes.utils.constants.Cloud_Error;
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


    /**
     * Click to layout drive info
     *
     * @param isAuth - check auth user
     */
    @Override
    public void clickInformationCloud(boolean isAuth) {
        if (isAuth) {

            getView().loadingLastBackupInfoCloud();
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

    @Override
    public void saveDataLoadingLastBackup(Drive mDriveCredential) {
        getDriveServiceHelper()
                .getLastBackupInfo(mDriveCredential)
                .addOnSuccessListener(lastInfo -> {
                    if (lastInfo.getErrorCode() == 0) {
                        getDataManager().getBackupCloudInfoPreference().setString(ARGUMENT_LAST_BACKUP_ID, lastInfo.getId());
                        getDataManager().getBackupCloudInfoPreference().setLong(ARGUMENT_LAST_BACKUP_TIME, lastInfo.getLastDate());
                        getView().editLastDataEditBackupCloud(lastInfo.getLastDate());
                    } else {
                        getView().showErrors(Cloud_Error.LAST_BACKUP_EMPTY);
                    }
                }).addOnFailureListener(fileList -> getView().showErrors(Cloud_Error.ERROR_LOAD_LAST_INFO_BACKUP));
    }

}
