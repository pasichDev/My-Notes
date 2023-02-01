package com.pasich.mynotes.worker;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.activity.BackupActivity;

public class AutoBackupCloudWorker extends Worker {

    public AutoBackupCloudWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        createNotif();
        return Result.success();
    }


    @Override
    public void onStopped() {
        super.onStopped();
    }


    public void createNotif() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(com.pasich.mynotes.utils.constants.NotificationChannel.AutoBackup_Chanel, getApplicationContext().getString(R.string.chanelAutoBackup), NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        Intent intent = new Intent(getApplicationContext(), BackupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), com.pasich.mynotes.utils.constants.NotificationChannel.AutoBackup_Chanel)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getApplicationContext().getString(R.string.workAutoBackupTitle))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setContentIntent(pendingIntent)
                .setTimeoutAfter(4000)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 20;
        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, true);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            return;
        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(0, builder.build());
        }

    }
}
