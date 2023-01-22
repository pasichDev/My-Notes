package com.pasich.mynotes.worker;

import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_DEFAULT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_TIME;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;
import static com.pasich.mynotes.utils.constants.Backup_Constants.FIlE_NAME_PREFERENCE_BACKUP;
import static com.pasich.mynotes.utils.constants.Drive_Scope.ACCESS_DRIVE_SCOPE;
import static com.pasich.mynotes.utils.constants.Drive_Scope.APPLICATION_NAME;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.gson.Gson;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DatabaseCustomConnect;
import com.pasich.mynotes.data.model.backup.JsonBackup;
import com.pasich.mynotes.ui.view.activity.BackupActivity;
import com.preference.PowerPreference;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class AutoBackupCloudWorker extends Worker {

    private final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
    private int resourceError;

    public AutoBackupCloudWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        createNotif();
        initialLoadFileCLoud();
        return Result.success();
    }


    public void initialLoadFileCLoud() {
        try {
            final java.io.File copyFileData = new java.io.File(getApplicationContext().getFilesDir() + FILE_NAME_BACKUP);
            BufferedWriter bwNote =
                    new BufferedWriter(new FileWriter(copyFileData));
            bwNote.write(Base64.encodeToString(getJsonData().getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
            bwNote.close();
            createAndLoadBackupCloud(copyFileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // TODO: 19.01.2023 Добавить вывод ошибок через переменную
    private Drive getCheckOnErrorReturnDrive() {
        if (acct == null) {
            resourceError = 1;
            return null;
        }
        if (!GoogleSignIn.hasPermissions(acct, ACCESS_DRIVE_SCOPE)) {
            resourceError = 2;
            return null;
        }

        Drive mDrive = new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                GoogleAccountCredential.usingOAuth2(getApplicationContext(), Collections.singleton(Scopes.DRIVE_APPFOLDER))
                        .setSelectedAccount(acct.getAccount())).setApplicationName(APPLICATION_NAME).build();

        if (mDrive == null) {
            resourceError = 0;
            return null;
        }

        return mDrive;
    }


    @Override
    public void onStopped() {
        super.onStopped();
    }

    // TODO: 19.01.2023 Реализовать валидацию json в AutoBackupCloudWorker
    private String getJsonData() {
        final DatabaseCustomConnect databaseCustomConnect = DatabaseCustomConnect.getInstance(getApplicationContext());
        final JsonBackup jsonBackupTemp = new JsonBackup();
        jsonBackupTemp.setNotes(databaseCustomConnect.noteDao().getNotesAllWorker());
        jsonBackupTemp.setTrashNotes(databaseCustomConnect.trashDao().getTrashWorker());
        jsonBackupTemp.setTags(databaseCustomConnect.tagsDao().getTagsWorker());
        return new Gson().toJson(jsonBackupTemp);
    }


    private void createAndLoadBackupCloud(java.io.File mFile) {
        final ArrayList<String> listIdsDeleted = new ArrayList<>();
        final String oldBackup = PowerPreference.getFileByName(FIlE_NAME_PREFERENCE_BACKUP).getString(ARGUMENT_LAST_BACKUP_ID, ARGUMENT_DEFAULT_LAST_BACKUP_ID);
        final Drive mDrive = getCheckOnErrorReturnDrive();
        if (mDrive == null) {
            //обновление оповещение и показ ошибки
        } else {
            final File fileMetadata = new File();
            final FileContent mediaContent = new FileContent("application/json", mFile);
            fileMetadata.setName(FILE_NAME_BACKUP);
            fileMetadata.setParents(Collections.singletonList("appDataFolder"));

            try {
                    FileList files = mDrive.files().list()
                            .setSpaces("appDataFolder")
                            .setFields("nextPageToken, files(id, name)")
                            .setPageSize(5)
                            .execute();

                    for (File file : files.getFiles()) {
                        listIdsDeleted.add(file.getId());
                    }


                File file = mDrive.files().create(fileMetadata, mediaContent).setFields("id").execute();
                if (file.getId().length() >= 2) {
                    for (String idDeleteBackup : listIdsDeleted) {
                        mDrive.files().delete(idDeleteBackup).execute();
                    }
                    PowerPreference.getFileByName(FIlE_NAME_PREFERENCE_BACKUP).setString(ARGUMENT_LAST_BACKUP_ID, file.getId());
                    PowerPreference.getFileByName(FIlE_NAME_PREFERENCE_BACKUP).setLong(ARGUMENT_LAST_BACKUP_TIME, new Date().getTime());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mFile.deleteOnExit();
    }


    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getApplicationContext().getString(R.string.chanelAutoBackup);
            String description = getApplicationContext().getString(R.string.chanelAutoBackupDescpt);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("autoBackup", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void createNotif() {

        createNotificationChannel();


        Intent intent = new Intent(getApplicationContext(), BackupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    /*    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_IMMUTABLE);


     */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "autoBackup")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getApplicationContext().getString(R.string.workAutoBackupTitle))
                .setContentText("20%")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                //  .setContentIntent(pendingIntent)
                .setTimeoutAfter(4000)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 20;
        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
// notificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            notificationManager.notify(0, builder.build());
        }

    }
}
