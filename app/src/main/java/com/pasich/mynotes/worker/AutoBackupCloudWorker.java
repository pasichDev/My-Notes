package com.pasich.mynotes.worker;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.pasich.mynotes.R;
import com.pasich.mynotes.utils.constants.Notifications;

public class AutoBackupCloudWorker extends Worker {
    private NotificationCompat.Builder notificationBuilder;


    public AutoBackupCloudWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    // TODO: 01.02.2023 Проверить ли есть права на оповещения в BackupActivitty
    @NonNull
    @Override
    public Result doWork() {
        startProcessBackup();
        return Result.success();
    }


    @Override
    public void onStopped() {
        super.onStopped();
    }


    public void startProcessBackup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Notifications.AutoBackup_Chanel, getApplicationContext().getString(R.string.chanelAutoBackup), NotificationManager.IMPORTANCE_DEFAULT);
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


    private void createBackup() {

    }

}
