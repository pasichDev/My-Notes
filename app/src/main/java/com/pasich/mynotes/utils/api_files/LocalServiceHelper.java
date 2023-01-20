package com.pasich.mynotes.utils.api_files;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.pasich.mynotes.di.scope.ApplicationContext;
import com.pasich.mynotes.utils.Base64ServiceHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
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
     * Создать temp file с данными json
     *
     * @param jsonValue
     * @return
     */
    public Task<File> createBackupJsonFile(String jsonValue) {
        TaskCompletionSource<File> taskCompletionSource = new TaskCompletionSource<>();

        new Thread(() -> {
            final File mFile = new File(mContext.getFilesDir() + FILE_NAME_BACKUP);

            try {
                new BufferedWriter(new FileWriter(mFile))
                        .write(Base64.encodeToString(jsonValue.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
            } catch (IOException e) {
                e.printStackTrace();
            }

            taskCompletionSource.setResult(mFile);
        });
        return taskCompletionSource.getTask();

    }


    /**
     * Запись файла в локальный публичый репозиторий (Download)
     *
     * @param jsonValue
     * @param uriLocalFile
     * @return
     */
    public boolean writeBackupLocalRepository(String jsonValue, Uri uriLocalFile) {
        Log.wtf(TAG, "writeBackupLocalRepository: " + jsonValue);
        try {
            ParcelFileDescriptor descriptor = mContext.getContentResolver().openFileDescriptor(uriLocalFile, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(descriptor.getFileDescriptor());

            fileOutputStream.write(Base64ServiceHelper.encodeString(jsonValue).getBytes());
            fileOutputStream.close();
            descriptor.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


}
