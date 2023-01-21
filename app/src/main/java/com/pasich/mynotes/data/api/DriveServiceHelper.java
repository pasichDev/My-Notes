package com.pasich.mynotes.data.api;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.pasich.mynotes.data.model.LastBackupCloud;
import com.pasich.mynotes.utils.constants.Backup_Constants;
import com.pasich.mynotes.utils.constants.Cloud_Error;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class DriveServiceHelper {


    private final Executor mExecutor = Executors.newSingleThreadExecutor();


    @Inject
    public DriveServiceHelper() {

    }

    public Task<LastBackupCloud> getLastBackupInfo(Drive mDriveCredential) {
        TaskCompletionSource<LastBackupCloud> task = new TaskCompletionSource<>();
        mExecutor.execute(() -> {
            try {
                FileList files = mDriveCredential.files().list().setSpaces("appDataFolder").setFields("nextPageToken, files(id, name, modifiedTime)").setOrderBy("modifiedTime").setPageSize(3).execute();

                if (files.size() == 0) {
                    task.setResult(new LastBackupCloud(Cloud_Error.LAST_BACKUP_EMPTY));
                } else {
                    for (File file : files.getFiles()) {
                        if (file.getName().equals(Backup_Constants.FILE_NAME_BACKUP)) {
                            task.setResult(new LastBackupCloud(file.getId(), file.getModifiedTime().getValue()));
                            return;
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return task.getTask();
    }


}
