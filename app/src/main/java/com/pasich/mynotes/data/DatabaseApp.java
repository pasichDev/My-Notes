package com.pasich.mynotes.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.dao.NoteDao;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;
import com.pasich.mynotes.data.trash.TrashNote;
import com.pasich.mynotes.data.trash.source.dao.TrashDao;
import com.pasich.mynotes.di.App;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.concurrent.Executor;

@Database(
    version = 1,
    entities = {Tag.class, Note.class, TrashNote.class})
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
                  TagsDao dao = sInstance.tagsDao();
                  dao.deleteAll();

                  dao.addTag(new Tag().create("", 1));
                  dao.addTag(new Tag().create("allNotes", 2));
                  Log.wtf("pasic", "createBD:   ");
              };


          executor.execute(runnable);
        }
      };

  public static synchronized DatabaseApp getInstance() {
    if (sInstance == null) {
      executor = new DiskExecutor();
      sInstance =
          Room.databaseBuilder(App.getInstance(), DatabaseApp.class, "MyNotes.db")
              .addCallback(sRoomDatabaseCallback)
              .build();
    }
    return sInstance;
  }

  public abstract TagsDao tagsDao();

  public abstract NoteDao noteDao();

  public abstract TrashDao trashDao();
}
