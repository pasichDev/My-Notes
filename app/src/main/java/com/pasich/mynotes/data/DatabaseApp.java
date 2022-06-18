package com.pasich.mynotes.data;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;
import com.pasich.mynotes.di.App;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.concurrent.Executor;

@Database(
    version = 1,
    entities = {Tag.class})
public abstract class DatabaseApp extends RoomDatabase {

  private static DatabaseApp sInstance;
  private static Executor executor;
  private static final RoomDatabase.Callback sRoomDatabaseCallback =
      new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
          super.onCreate(db);
          Runnable runnable =
              () -> {

                // If you want to start with more words, just add them.
                TagsDao dao = sInstance.TagsDao();
                dao.deleteAll();

                dao.addTag(new Tag().create("", 1, false));
                dao.addTag(new Tag().create("allNotes", 2, true));
              };
          executor.execute(runnable);
        }
      };

  public static DatabaseApp getInstance() {
    if (sInstance == null) {
      executor = new DiskExecutor();
      sInstance =
          Room.databaseBuilder(App.getInstance(), DatabaseApp.class, "MyNotes.db")
              .addCallback(sRoomDatabaseCallback)
              .build();
    }
    return sInstance;
  }

  public abstract TagsDao TagsDao();
}
