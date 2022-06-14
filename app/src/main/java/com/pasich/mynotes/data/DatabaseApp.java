package com.pasich.mynotes.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;
import com.pasich.mynotes.di.App;

@Database(
    version = 2,
    entities = {Tag.class})
public abstract class DatabaseApp extends RoomDatabase {

  public abstract TagsDao getTagsDao();

  private static DatabaseApp sInstance;

  public static DatabaseApp getInstance() {
    if (sInstance == null) {
      sInstance = Room.databaseBuilder(App.getInstance(), DatabaseApp.class, "MyNotes.db").build();
    }
    return sInstance;
  }
}
