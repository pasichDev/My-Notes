package com.pasich.mynotes.utils.api_files;

import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.pasich.mynotes.di.scope.ApplicationContext;
import com.pasich.mynotes.utils.BackupServiceCache;
import com.pasich.mynotes.utils.Base64ServiceHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

public class LocalServiceHelper {

    private final Context mContext;

    @Inject
    public LocalServiceHelper(@ApplicationContext Context context) {
        this.mContext = context;
    }

    /**
     * Write public json file, intent(Document)
     *
     * @param serviceCache - cache backup initial user data
     * @param uriLocalFile - uri public file
     * @return - errorStatus
     */
    public boolean writeBackupLocalFile(BackupServiceCache serviceCache, Uri uriLocalFile) {
        if (!checkServiceCache(serviceCache)) {
            try {
                ParcelFileDescriptor descriptor = mContext.getContentResolver().openFileDescriptor(uriLocalFile, "w");
                FileOutputStream fileOutputStream = new FileOutputStream(descriptor.getFileDescriptor());
                fileOutputStream.write(Base64ServiceHelper.encodeString(serviceCache.getJsonBackup()).getBytes());
                fileOutputStream.close();
                descriptor.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }

    }


    /**
     * Write public json file, intent(Document)
     *
     * @param serviceCache - cache backup initial user data
     * @param uriLocalFile - uri public file
     * @return - errorStatus
     */
    public String readBackupLocalFile(BackupServiceCache serviceCache, Uri uriLocalFile) {
        try {
            final ParcelFileDescriptor descriptor = mContext.getContentResolver().openFileDescriptor(uriLocalFile, "r");
            final StringBuilder jsonFile = new StringBuilder();
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(descriptor.getFileDescriptor()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonFile.append(line);
                jsonFile.append('\n');
            }

            descriptor.close();
            bufferedReader.close();
            return Base64ServiceHelper.decodeString(jsonFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Valid service cache
     *
     * @param serviceCache - cache backup initial user data
     * @return - error check
     */
    private boolean checkServiceCache(BackupServiceCache serviceCache) {
        if (serviceCache == null) {
            return true;
        }
        if (serviceCache.getJsonBackup().length() < 10) {
            serviceCache.setJsonBackup("");
            return true;
        }
        return false;
    }


    /**
     * Создать temp file с данными json
     *
     * @return
     */
    public Task<File> createBackupJsonFile(BackupServiceCache serviceCache) {
        TaskCompletionSource<File> taskCompletionSource = new TaskCompletionSource<>();

        new Thread(() -> {
            final File mFile = new File(mContext.getFilesDir() + FILE_NAME_BACKUP);

            try {
                new BufferedWriter(new FileWriter(mFile))
                        .write(Base64.encodeToString(serviceCache.getJsonBackup().getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
            } catch (IOException e) {
                e.printStackTrace();
            }

            taskCompletionSource.setResult(mFile);
        });
        return taskCompletionSource.getTask();

    }
}
