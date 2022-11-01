package com.pasich.mynotes.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.pasich.mynotes.data.AppDataManger;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.AppDatabase;
import com.pasich.mynotes.data.database.AppDbHelper;
import com.pasich.mynotes.data.database.DbHelper;
import com.pasich.mynotes.di.scope.ApplicationContext;
import com.pasich.mynotes.di.scope.DatabaseInfo;
import com.pasich.mynotes.utils.constants.DB_Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context providesContext() {
        return application;
    }

    @Provides
    Application providesApplication() {
        return application;
    }


    @Provides
    @DatabaseInfo
    String providesDatabaseName() {
        return DB_Constants.DB_NAME;
    }

    @Provides
    @DatabaseInfo
    Integer providesDatabaseVersion() {
        return DB_Constants.DB_VERSION;
    }


    @Provides
    @Singleton
    AppDatabase providesAppDatabase(@ApplicationContext Context context,
                                    @DatabaseInfo String dbName) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, dbName)
                //  .addCallback(sRoomDatabaseCallback)
                .build();
    }


    /*
    @Provides
    @DatabaseInfo
    RoomDatabase.Callback providerRoomDatabaseCallback(DataManager dataManager){
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Runnable runnable = () -> {
                    dataManager.deleteAll();
                    dataManager.addTag(new Tag().create("", 1));
                    dataManager.addTag(new Tag().create("allNotes", 2));
                };


                Executors.newSingleThreadExecutor().execute(runnable);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
    }


     */


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


}
