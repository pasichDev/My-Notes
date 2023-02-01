package com.pasich.mynotes.worker;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DatabaseCustomConnect;
import com.pasich.mynotes.data.model.backup.JsonBackup;
import com.pasich.mynotes.utils.constants.Notifications;

import java.util.concurrent.TimeUnit;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

@HiltWorker
public class AutoBackupCloudWorker extends Worker {
    private NotificationCompat.Builder notificationBuilder;


    @AssistedInject
    public AutoBackupCloudWorker(@Assisted @NonNull Context context, @Assisted @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    // TODO: 01.02.2023 Проверить ли есть права на оповещения в BackupActivitty
    @NonNull
    @Override
    public Result doWork() {
        startProcessBackup();


        Log.d(TAG, "doWork: start");

        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                Log.d(TAG, i + ", isStopped " + isStopped());
                if (isStopped()) return Result.failure();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "doWork: end");

        return Result.success();
    }


    @Override
    public void onStopped() {
        super.onStopped();
        Log.d(TAG, "onStopped");
    }


    public void startProcessBackup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Notifications.AutoBackup_Chanel, getApplicationContext().getString(R.string.chanelAutoBackup), NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), Notifications.AutoBackup_Chanel)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getApplicationContext().getString(R.string.workAutoBackupTitle))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 20;
        notificationBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, true);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

        } else {
            NotificationManagerCompat.from(getApplicationContext()).notify(Notifications.AutoBackup_NotificationId, notificationBuilder.build());
        }
    }


    private JsonBackup createJsonBackupData() {
        JsonBackup jsonBackupTemp = new JsonBackup();
        DatabaseCustomConnect databaseCustomConnect = DatabaseCustomConnect.getInstance(getApplicationContext());
        jsonBackupTemp.setNotes(databaseCustomConnect.noteDao().getNotesAllWorker());
        jsonBackupTemp.setTags(databaseCustomConnect.tagsDao().getTagsWorker());
        jsonBackupTemp.setTags(databaseCustomConnect.tagsDao().getTagsWorker());
        return jsonBackupTemp;
    }


    private void createDataToDrive() {

    }
}
