package com.pasich.mynotes.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.pasich.mynotes.data.database.dao.NoteDao;
import com.pasich.mynotes.data.database.dao.TagsDao;
import com.pasich.mynotes.data.database.dao.TrashDao;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.utils.constants.DB_Constants;

import javax.inject.Singleton;

@Database(version = DB_Constants.DB_VERSION, entities = {Tag.class, Note.class, TrashNote.class})
@Singleton
public abstract class AppDatabase extends RoomDatabase {

    public abstract TagsDao tagsDao();

    public abstract NoteDao noteDao();

    public abstract TrashDao trashDao();
}
