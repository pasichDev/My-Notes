package com.pasich.mynotes.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.dao.NoteDao;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;
import com.pasich.mynotes.data.trash.TrashNote;
import com.pasich.mynotes.data.trash.source.dao.TrashDao;
import com.pasich.mynotes.utils.constants.DB_Constants;

import javax.inject.Singleton;

@Database(version = DB_Constants.DB_VERSION, entities = {Tag.class, Note.class, TrashNote.class})
@Singleton
public abstract class AppDatabase extends RoomDatabase {

    public abstract TagsDao tagsDao();

    public abstract NoteDao noteDao();

    public abstract TrashDao trashDao();
}
