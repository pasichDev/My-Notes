package com.pasich.mynotes.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pasich.mynotes.data.database.dao.NoteDao;
import com.pasich.mynotes.data.database.dao.TagsDao;
import com.pasich.mynotes.data.database.dao.TrashDao;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.data.model.Tag;
import com.pasich.mynotes.data.model.TrashNote;
import com.pasich.mynotes.utils.constants.DB_Constants;

@Database(version = DB_Constants.DB_VERSION, entities = {Tag.class, Note.class, TrashNote.class})
public abstract class DatabaseCustomConnect extends RoomDatabase {

    private static DatabaseCustomConnect sInstance;

    public static DatabaseCustomConnect getInstance(Context context) {
        if (sInstance == null) {
            sInstance =
                    Room.databaseBuilder(context, DatabaseCustomConnect.class, DB_Constants.DB_NAME)
                            .build();
        }
        return sInstance;
    }

    public abstract TagsDao tagsDao();

    public abstract NoteDao noteDao();

    public abstract TrashDao trashDao();
}