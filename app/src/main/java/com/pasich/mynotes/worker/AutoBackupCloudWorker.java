package com.pasich.mynotes.worker;

import static android.content.ContentValues.TAG;
import static com.pasich.mynotes.utils.constants.BackupPreferences.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.BackupPreferences.ARGUMENT_LAST_BACKUP_TIME;

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

import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.services.drive.Drive;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.model.backup.JsonBackup;
import com.pasich.mynotes.utils.backup.CloudAuthHelper;
import com.pasich.mynotes.utils.backup.CloudCacheHelper;
import com.pasich.mynotes.utils.constants.Notifications;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@HiltWorker
public class AutoBackupCloudWorker extends Worker {

    private NotificationCompat.Builder notificationBuilder;
    private final DataManager dataManager;
    private final int PROGRESS_MAX = 100;
    public CloudCacheHelper cloudCacheHelper;
    public CloudAuthHelper cloudAuthHelper;


    @AssistedInject
    public AutoBackupCloudWorker(@Assisted @NonNull Context ctx, @Assisted @NonNull WorkerParameters wp, DataManager dm,
                                 CloudCacheHelper cch, CloudAuthHelper cah) {
        super(ctx, wp);
        this.dataManager = dm;
        this.cloudCacheHelper = cch;
        this.cloudAuthHelper = cah;
    }

    // TODO: 01.02.2023 Проверить ли есть права на оповещения в BackupActivitty
    @NonNull
    @Override
    public Result doWork() {
        startProcessBackup();

/*
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


 */
        return Result.success();
    }


    @Override
    public void onStopped() {
        super.onStopped();
        Log.d(TAG, "onStopped");
    }


    // TODO: 02.02.2023 Нужно повесить .subscribeOn(getSchedulerProvider().io())
    //                        .observeOn(getSchedulerProvider().ui())

    private void createJsonBackupData() {
        JsonBackup jsonBackupTemp = new JsonBackup();
        // resultWorker resultRequest = new resultWorker();
        Log.wtf(TAG, "work create json: ");
        Disposable disposable = Flowable.zip(
                        dataManager.getNotes(),
                        dataManager.getTrashNotesLoad(),
                        dataManager.getTagsUser(),
                        (noteList, trashNoteList, tagList) -> {
                            jsonBackupTemp.setNotes(noteList);
                            jsonBackupTemp.setTrashNotes(trashNoteList);
                            jsonBackupTemp.setTags(tagList);
                            return noteList.size() + trashNoteList.size() + tagList.size();
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countData -> {
                    Log.wtf(TAG, "work subscribe: ");
                    ///здесь если пусто по данних не делать запрос
                    //     resultRequest.setResult(createDataToDrive(jsonBackupTemp, getDrive()));
                    createDataToDrive(jsonBackupTemp, getDrive());
                });
        // disposable.dispose();
        //   return resultRequest.getResult();
    }


    private void createDataToDrive(JsonBackup jsonBackup, Drive mDriveCredential) {
        final java.io.File backupTemp = dataManager.writeTempBackup(jsonBackup);
        if (backupTemp == null) {
            Log.wtf(TAG, "work error: ");
            /// return true;
        } else {
            Log.wtf(TAG, "work start: ");
            dataManager
                    .getOldBackupForCLean(mDriveCredential) //load old backup
                    .onSuccessTask(listBackups ->
                            dataManager.writeCloudBackup(mDriveCredential,
                                            backupTemp, getProcessListener())
                                    .addOnCompleteListener(stack -> {
                                        backupTemp.delete();
                                    })
                                    .addOnSuccessListener(backupCloud -> {
                                        dataManager.getBackupCloudInfoPreference().putString(ARGUMENT_LAST_BACKUP_ID, backupCloud.getId()).putLong(ARGUMENT_LAST_BACKUP_TIME, backupCloud.getLastDate());
                                    })
                                    .onSuccessTask(backupCloud ->

                                            dataManager.cleanOldBackups(mDriveCredential, listBackups))
                                    .addOnFailureListener(stack -> canceledWork(true))

                                    .addOnFailureListener(stack -> {

                                        backupTemp.delete();
                                    }));

        }

        //   return canceledWork(true);
    }

    private boolean canceledWork(boolean error) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

        } else {
            NotificationManagerCompat.from(getApplicationContext()).cancel(Notifications.AutoBackup_NotificationId);
        }
        Log.wtf(TAG, "work error: reste ");
        return error;
    }


    public MediaHttpUploaderProgressListener getProcessListener() {
        return uploading -> {
            switch (uploading.getUploadState()) {
                case INITIATION_STARTED -> {
                    notificationBuilder.setProgress(PROGRESS_MAX, 30, true);
                }
                case INITIATION_COMPLETE -> {
                    notificationBuilder.setProgress(PROGRESS_MAX, 50, true);
                }
                case MEDIA_IN_PROGRESS -> {
                    notificationBuilder.setProgress(PROGRESS_MAX, 70, true);
                }
                case MEDIA_COMPLETE -> {
                    notificationBuilder.setProgress(PROGRESS_MAX, 99, true);
                }
            }
        };
    }


    public Drive getDrive() {
        return cloudAuthHelper.getDriveCredentialService(
                cloudCacheHelper.isAuth() ? cloudCacheHelper.getGoogleSignInAccount().getAccount() : null,
                getApplicationContext());
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


        notificationBuilder.setProgress(PROGRESS_MAX, 10, true);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

        } else {
            NotificationManagerCompat.from(getApplicationContext()).notify(Notifications.AutoBackup_NotificationId, notificationBuilder.build());
        }
        createJsonBackupData();
    }

    private class resultWorker {
        private Result result;


        public void setResult(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return result;
        }
    }
}
