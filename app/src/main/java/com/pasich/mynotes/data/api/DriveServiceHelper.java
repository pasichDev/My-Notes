package com.pasich.mynotes.data.api;

import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_DEFAULT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FIlE_NAME_PREFERENCE_BACKUP;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.pasich.mynotes.data.model.BackupCloud;
import com.pasich.mynotes.data.model.LastBackupCloud;
import com.pasich.mynotes.utils.backup.BackupCacheHelper;
import com.pasich.mynotes.utils.constants.Cloud_Error;
import com.preference.PowerPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class DriveServiceHelper {


    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final int PAGE_SIZE = 5;


    @Inject
    public DriveServiceHelper() {

    }

    /**
     * Check info lastBackup
     *
     * @param mDriveCredential - drive permissions check
     */
    public Task<LastBackupCloud> getLastBackupInfo(Drive mDriveCredential) {
        final ArrayList<LastBackupCloud> list = new ArrayList<>();
        TaskCompletionSource<LastBackupCloud> task = new TaskCompletionSource<>();
        mExecutor.execute(() -> {
            try {
                FileList files = mDriveCredential.files().list().setSpaces("appDataFolder")
                        .setFields("files(id, modifiedTime)").setOrderBy("modifiedTime")
                        .setPageSize(PAGE_SIZE)
                        .execute();

                for (File file : files.getFiles()) {
                    list.add(new LastBackupCloud(file.getId(), file.getModifiedTime().getValue()));
                }

                if (list.size() == 0) {
                    task.setResult(new LastBackupCloud(Cloud_Error.LAST_BACKUP_EMPTY_DRIVE_VIEW));
                } else {
                    task.setResult(list.get(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return task.getTask();
    }


    public Task<BackupCloud> writeCloudBackup(Drive mDriveCredential, java.io.File backupTemp, MediaHttpUploaderProgressListener progressListener) {
        final TaskCompletionSource<BackupCloud> task = new TaskCompletionSource<>();
        mExecutor.execute(() -> {
            final File fileMetadata = new File().setName(FILE_NAME_BACKUP).setParents(Collections.singletonList("appDataFolder"));
            final FileContent mediaContent = new FileContent("application/json", backupTemp);

            try {
                Drive.Files.Create create = mDriveCredential.files().create(fileMetadata, mediaContent).setFields("id");
                MediaHttpUploader uploader = create.getMediaHttpUploader();
                uploader.setProgressListener(progressListener);
                File file = create.execute();
                task.setResult(new BackupCloud(file.getId(), new Date().getTime()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return task.getTask();
    }

    /**
     * Clean old backup copy
     *
     * @param mDriveCredential - drive permissions check
     * @param oldBackups       - array old backups ids
     */
    public void cleanOldBackups(Drive mDriveCredential, ArrayList<String> oldBackups) {
        mExecutor.execute(() -> {
            try {
                for (String idDeleteBackup : oldBackups) {
                    mDriveCredential.files().delete(idDeleteBackup).execute();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    // TODO: 21.01.2023 #1 если нет файла то пропустим чтобы небыло бага

    /**
     * Load oldBackup list
     *
     * @param mDriveCredential - drive permissions check
     */
    public Task<ArrayList<String>> getOldBackupForCLean(Drive mDriveCredential) {
        final ArrayList<String> listIdsDeleted = new ArrayList<>();
        final String oldBackup = PowerPreference.getFileByName(FIlE_NAME_PREFERENCE_BACKUP).getString(ARGUMENT_LAST_BACKUP_ID, ARGUMENT_DEFAULT_LAST_BACKUP_ID);
        final TaskCompletionSource<ArrayList<String>> task = new TaskCompletionSource<>();
        mExecutor.execute(() -> {
            try {
                //1
                if (!oldBackup.equals("null")) {
                    listIdsDeleted.add(mDriveCredential.files().get(oldBackup).setFields("files(id)").getFileId());
                } else {
                    FileList files = mDriveCredential.files().list().setSpaces("appDataFolder")
                            .setFields("files(id)").setPageSize(PAGE_SIZE).execute();

                    for (File file : files.getFiles()) {
                        listIdsDeleted.add(file.getId());
                    }
                }
                task.setResult(listIdsDeleted);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return task.getTask();
    }

    public Task<BackupCacheHelper> getOldBackupCloud(Drive mDriveCredential) {
        final String oldBackup = PowerPreference.getFileByName(FIlE_NAME_PREFERENCE_BACKUP).getString(ARGUMENT_LAST_BACKUP_ID, ARGUMENT_DEFAULT_LAST_BACKUP_ID);
        final TaskCompletionSource<BackupCacheHelper> task = new TaskCompletionSource<>();
    /*       mExecutor.execute(() -> {
            try {
             if (!oldBackup.equals("null")) {
                    listIdsDeleted.add(mDriveCredential.files().get(oldBackup).getFileId());
                } else {
                    FileList files = mDriveCredential.files().list().setSpaces("appDataFolder").setFields("nextPageToken, files(id, name)").setPageSize(3).execute();

                    for (File file : files.getFiles()) {
                        listIdsDeleted.add(file.getId());
                    }
                }

                task.setResult(listIdsDeleted);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }); */
        return task.getTask();
    }

}
