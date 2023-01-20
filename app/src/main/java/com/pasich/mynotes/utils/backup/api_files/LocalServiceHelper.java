package com.pasich.mynotes.utils.backup.api_files;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.pasich.mynotes.data.model.JsonBackup;
import com.pasich.mynotes.di.scope.ApplicationContext;
import com.pasich.mynotes.utils.backup.BackupCacheHelper;
import com.pasich.mynotes.utils.backup.ScramblerBackupHelper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

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
    public boolean writeBackupLocalFile(BackupCacheHelper serviceCache, Uri uriLocalFile) {
        if (!checkServiceCache(serviceCache)) {
            try {
                ParcelFileDescriptor descriptor = mContext.getContentResolver().openFileDescriptor(uriLocalFile, "w");
                FileOutputStream fileOutputStream = new FileOutputStream(descriptor.getFileDescriptor());
                fileOutputStream.write(ScramblerBackupHelper.encodeString(serviceCache.getJsonBackup()).getBytes());
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
     * @param uriLocalFile - uri public file
     * @return - errorStatus
     */
    public JsonBackup readBackupLocalFile(Uri uriLocalFile) {
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
            return ScramblerBackupHelper.decodeString(jsonFile.toString());
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
    private boolean checkServiceCache(BackupCacheHelper serviceCache) {
        if (serviceCache == null) {
            return true;
        }
        return serviceCache.getJsonBackup() == null;
    }


}
