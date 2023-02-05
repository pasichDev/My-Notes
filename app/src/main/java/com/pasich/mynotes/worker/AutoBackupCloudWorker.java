package com.pasich.mynotes.worker;

import static android.content.ContentValues.TAG;
import static com.pasich.mynotes.utils.constants.BackupPreferences.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.BackupPreferences.ARGUMENT_LAST_BACKUP_TIME;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.pasich.mynotes.ui.view.activity.BackupActivity;
import com.pasich.mynotes.utils.backup.CloudAuthHelper;
import com.pasich.mynotes.utils.backup.CloudCacheHelper;
import com.pasich.mynotes.utils.constants.CloudErrors;
import com.pasich.mynotes.utils.constants.Notifications;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@HiltWorker
public class AutoBackupCloudWorker extends Worker {

    private final DataManager dataManager;
    public CloudCacheHelper cloudCacheHelper;
    public CloudAuthHelper cloudAuthHelper;

    public CompositeDisposable compositeDisposable;

    @AssistedInject
    public AutoBackupCloudWorker(@Assisted @NonNull Context ctx, @Assisted @NonNull WorkerParameters wp, DataManager dm, CloudCacheHelper cch, CloudAuthHelper cah) {
        super(ctx, wp);
        this.dataManager = dm;
        this.cloudCacheHelper = cch;
        this.cloudAuthHelper = cah;
        this.compositeDisposable = new CompositeDisposable();
    }


    // TODO: 05.02.2023 Начить воркер если ошибка перепланироывать

    @NonNull
    @Override
    public Result doWork() {
        if (isStopped()) return Result.failure();
        creteProcessNotification();
        createJsonBackupData();
        return Result.success();
    }


    @Override
    public void onStopped() {
        super.onStopped();
        Log.wtf(TAG, "onStopped: worker stoop");
        compositeDisposable.dispose();
    }

    private void createJsonBackupData() {
        JsonBackup jsonBackupTemp = new JsonBackup();
        compositeDisposable.add(Flowable.zip(dataManager.getNotes(), dataManager.getTrashNotesLoad(), dataManager.getTagsUser(), (noteList, trashNoteList, tagList) -> {
            jsonBackupTemp.setNotes(noteList);
            jsonBackupTemp.setTrashNotes(trashNoteList);
            jsonBackupTemp.setTags(tagList);
            return noteList.size() + trashNoteList.size() + tagList.size();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(countData -> {
            if (countData != 0) {
                createDataToDrive(jsonBackupTemp, getDrive());
            }
        }));
    }


    private void createDataToDrive(JsonBackup jsonBackup, Drive mDriveCredential) {
        final java.io.File backupTemp = dataManager.writeTempBackup(jsonBackup);
        if (backupTemp == null) {
            finishWorker(CloudErrors.ERROR_CREATE_CLOUD_BACKUP);
        } else {
            dataManager.getOldBackupForCLean(mDriveCredential).onSuccessTask(listBackups -> dataManager.writeCloudBackup(mDriveCredential, backupTemp, getProcessListener()).addOnSuccessListener(backupCloud -> {
                finishWorker(CloudErrors.NO_ERROR);
                dataManager.getBackupCloudInfoPreference().putString(ARGUMENT_LAST_BACKUP_ID, backupCloud.getId()).putLong(ARGUMENT_LAST_BACKUP_TIME, backupCloud.getLastDate());
            }).onSuccessTask(backupCloud -> dataManager.cleanOldBackups(mDriveCredential, listBackups)).addOnFailureListener(stack -> finishWorker(CloudErrors.ERROR_CREATE_CLOUD_BACKUP)).addOnFailureListener(stack -> finishWorker(CloudErrors.ERROR_CREATE_CLOUD_BACKUP))).addOnCompleteListener(task -> backupTemp.delete());

        }
    }


    private void finishWorker(int errorCode) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(getApplicationContext()).cancel(Notifications.AutoBackup_NotificationId);
        }
        if (errorCode != CloudErrors.NO_ERROR) {
            showErrorNotification(errorCode);
        }
        compositeDisposable.dispose();
    }

    public Drive getDrive() {
        return cloudAuthHelper.getDriveCredentialService(cloudCacheHelper.isAuth() ? cloudCacheHelper.getGoogleSignInAccount().getAccount() : null, getApplicationContext());
    }

    public void creteProcessNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Notifications.AutoBackup_Chanel, getApplicationContext().getString(R.string.chanelAutoBackup), NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(getApplicationContext()).notify(Notifications.AutoBackup_NotificationId, new NotificationCompat.Builder(getApplicationContext(), Notifications.AutoBackup_Chanel).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(getApplicationContext().getString(R.string.workAutoBackupTitle)).setPriority(NotificationCompat.PRIORITY_HIGH).setProgress(100, 10, true).build());
        }
    }

    public void showErrorNotification(int errorCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Notifications.AutoBackup_Chanel, getApplicationContext().getString(R.string.chanelAutoBackup), NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), BackupActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK), PendingIntent.FLAG_IMMUTABLE);

            NotificationManagerCompat.from(getApplicationContext()).notify(Notifications.AutoBackup_NotificationId, new NotificationCompat.Builder(getApplicationContext(), Notifications.AutoBackup_Chanel).setSmallIcon(R.mipmap.ic_launcher).setStyle(new NotificationCompat.BigTextStyle().bigText(getErrorText(errorCode))).setContentText(getErrorText(errorCode)).setContentTitle(getApplicationContext().getString(R.string.error)).setContentIntent(pendingIntent).setPriority(NotificationCompat.PRIORITY_HIGH).build());
        }
    }

    private String getErrorText(int errorCode) {
        return getApplicationContext().getString(R.string.errorWorker);
    }


    public MediaHttpUploaderProgressListener getProcessListener() {
        return uploading -> {

        };
    }
}
