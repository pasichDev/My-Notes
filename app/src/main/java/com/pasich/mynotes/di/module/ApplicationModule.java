package com.pasich.mynotes.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.pasich.mynotes.data.newdata.AppDataManger;
import com.pasich.mynotes.data.newdata.AppDatabase;
import com.pasich.mynotes.data.newdata.AppDbHelper;
import com.pasich.mynotes.data.newdata.DataManger;
import com.pasich.mynotes.data.newdata.DbHelper;
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
    AppDatabase providesAppDatabase(@ApplicationContext Context context, @DatabaseInfo String dbName) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, dbName).build();
    }


    @Provides
    @Singleton
    DbHelper providesDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }


    @Provides
    @Singleton
    DataManger providesDataManager(AppDataManger appDataManager) {
        return appDataManager;
    }

}
