package com.pasich.mynotes.di;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.pasich.mynotes.data.AppDataManger;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.AppDatabase;
import com.pasich.mynotes.data.database.AppDbHelper;
import com.pasich.mynotes.data.database.DbHelper;
import com.pasich.mynotes.utils.backup.CloudCacheHelper;
import com.pasich.mynotes.utils.constants.Database;
import com.pasich.mynotes.utils.constants.DriveScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ApplicationModule {

    @Provides
    @Singleton
    AppDatabase providesAppDatabase(@ApplicationContext Context context,
                                    RoomDatabase.Callback sRoomDatabaseCallback,
                                    Migration migration2_3) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Database.DB_NAME)
                .addCallback(sRoomDatabaseCallback).addMigrations(migration2_3).build();
    }


    @Provides
    @Singleton
    RoomDatabase.Callback providerRoomDatabaseCallback() {
        return new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                db.execSQL("INSERT INTO  tags (name,visibility,systemAction) VALUES ('',0,1)");
                db.execSQL("INSERT INTO  tags (name,visibility,systemAction) VALUES ('allNotes',0,2)");

            }


            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
    }


    @Provides
    @Singleton
    DbHelper providesDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }


    @Provides
    @Singleton
    DataManager providesDataManager(AppDataManger appDataManager) {
        return appDataManager;
    }


    @Provides
    @Singleton
    Scope provideCloudAccessDriveScope() {
        return DriveScope.ACCESS_DRIVE_SCOPE;
    }


    @Provides
    @Singleton
    GoogleSignInClient providesGoogleSignInClient(@ApplicationContext Context mContext, Scope accessDrive) {
        return GoogleSignIn.getClient(mContext, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(accessDrive).build());
    }

    @Provides
    @Singleton
    boolean providerIsPlayStoreInstalled(@ApplicationContext Context context) {
        boolean flag;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.android.vending", 0);
            flag = packageInfo.applicationInfo.enabled;
        } catch (PackageManager.NameNotFoundException exc) {
            flag = false;
        }
        return flag;
    }


    @Provides
    @Singleton
    CloudCacheHelper providesCloudCacheHelper(@ApplicationContext Context mContext, Scope accessDrive, boolean isPlayMarketInstall) {
        if (isPlayMarketInstall) {
            GoogleSignInAccount mLastAccount = GoogleSignIn.getLastSignedInAccount(mContext);
            if (mLastAccount != null) {
                return new CloudCacheHelper().build(mLastAccount, GoogleSignIn.hasPermissions(mLastAccount, accessDrive));
            } else {
                return new CloudCacheHelper();
            }
        } else {
            return new CloudCacheHelper().playMarketNoInstall();
        }
    }

    @Provides
    @Singleton
    Migration providesMigration2_3() {
        return new Migration(2, 3) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("CREATE TABLE `listItemsNote` (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    value TEXT,\n" +
                        "    idNote INTEGER,\n" +
                        "    dragPosition INTEGER,\n" +
                        "    isChecked INTEGER,\n" +
                        "    isTrash INTEGER)");
            }
        };
    }


}
