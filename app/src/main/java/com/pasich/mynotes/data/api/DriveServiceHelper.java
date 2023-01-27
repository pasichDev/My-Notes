package com.pasich.mynotes.data.api;

import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_DEFAULT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FIlE_NAME_PREFERENCE_BACKUP;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.pasich.mynotes.data.model.backup.BackupCloud;
import com.pasich.mynotes.data.model.backup.JsonBackup;
import com.pasich.mynotes.data.model.backup.LastBackupCloud;
import com.pasich.mynotes.utils.backup.ScramblerBackupHelper;
import com.pasich.mynotes.utils.constants.Cloud_Error;
import com.preference.PowerPreference;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class DriveServiceHelper {


    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final int PAGE_SIZE = 6;


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
        return Tasks.call(mExecutor, () -> {
            FileList files = mDriveCredential.files().list().setSpaces("appDataFolder")
                    .setFields("files(id, modifiedTime)").setOrderBy("modifiedTime desc")
                    .setPageSize(PAGE_SIZE)
                    .execute();

            for (File file : files.getFiles()) {
                list.add(new LastBackupCloud(file.getId(), file.getModifiedTime().getValue()));
            }

            if (list.size() == 0) {
                return new LastBackupCloud(Cloud_Error.LAST_BACKUP_EMPTY_DRIVE_VIEW);
            } else {
                return list.get(0);
            }
        });
    }


    /**
     * Write file backup to cloud
     *
     * @param mDriveCredential - check auth user
     * @param backupTemp       - temp file backup
     * @param progressListener - listener progress upload
     */
    public Task<BackupCloud> writeCloudBackup(Drive mDriveCredential, java.io.File backupTemp, MediaHttpUploaderProgressListener progressListener) {
        return Tasks.call(mExecutor, () -> {
            final File fileMetadata = new File().setName(FILE_NAME_BACKUP).setParents(Collections.singletonList("appDataFolder"));
            final FileContent mediaContent = new FileContent("application/json", backupTemp);

            Drive.Files.Create create = mDriveCredential.files().create(fileMetadata, mediaContent).setFields("id");
            MediaHttpUploader uploader = create.getMediaHttpUploader();
            uploader.setProgressListener(progressListener);
            File file = create.execute();
            return new BackupCloud(file.getId(), new Date().getTime());
        });
    }

    /**
     * Clean old backup copy
     *
     * @param mDriveCredential - drive permissions check
     * @param oldBackups       - array old backups ids
     */
    public Task<Boolean> cleanOldBackups(Drive mDriveCredential, ArrayList<String> oldBackups) {
        return Tasks.call(mExecutor, () -> {
            for (String idDeleteBackup : oldBackups) {
                mDriveCredential.files().delete(idDeleteBackup).execute();
            }
            return true;
        });
    }


    /**
     * Load oldBackup list
     *
     * @param mDriveCredential - drive permissions check
     */
    public Task<ArrayList<String>> getOldBackupForCLean(Drive mDriveCredential) {
        final ArrayList<String> listIdsDeleted = new ArrayList<>();
        return Tasks.call(mExecutor, () -> {

            FileList files = mDriveCredential.files().list().setSpaces("appDataFolder")
                        .setFields("files(id)").setPageSize(PAGE_SIZE).execute();

                for (File file : files.getFiles()) {
                    listIdsDeleted.add(file.getId());
                }

            return listIdsDeleted;
        });
    }

    public Task<JsonBackup> getReadLastBackupCloud(Drive mDriveCredential) {
        final String idBackupCloud = PowerPreference.getFileByName(FIlE_NAME_PREFERENCE_BACKUP).getString(ARGUMENT_LAST_BACKUP_ID, ARGUMENT_DEFAULT_LAST_BACKUP_ID);
        return Tasks.call(mExecutor, () -> {
            OutputStream outputStream = new ByteArrayOutputStream();
            mDriveCredential.files().get(idBackupCloud).executeMediaAndDownloadTo(outputStream);
            return ScramblerBackupHelper.decodeString(outputStream.toString());
        });
    }

}
