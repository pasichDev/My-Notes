package com.pasich.mynotes.data;


import com.pasich.mynotes.data.database.DbHelper;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.data.old.notes.Note;
import com.pasich.mynotes.data.preferences.AppPreferencesHelper;
import com.preference.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class AppDataManger implements DataManger {


    private final DbHelper dbHelper;
    private final AppPreferencesHelper preferencesHelper;

    @Inject
    AppDataManger(AppPreferencesHelper preferencesHelper, DbHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.preferencesHelper = preferencesHelper;
    }


    /**
     * Preferences
     */
    @Override
    public Preference getDefaultPreferences() {
        return preferencesHelper.getDefaultPreferences();
    }

    @Override
    public int getFormatCount() {
        return preferencesHelper.getFormatCount();
    }

    @Override
    public int getSizeTextNoteActivity() {
        return preferencesHelper.getSizeTextNoteActivity();
    }

    @Override
    public String getTypeFaceNoteActivity() {
        return preferencesHelper.getTypeFaceNoteActivity();
    }


    /**
     * Tags
     */

    @Override
    public Observable<List<Tag>> getTags() {
        return dbHelper.getTags();
    }

    @Override
    public Observable<List<Tag>> getTagsUser() {
        return dbHelper.getTagsUser();
    }

    @Override
    public Observable<Integer> getCountTagAll() {
        return dbHelper.getCountTagAll();
    }

    @Override
    public Completable addTag(Tag tag) {
        return dbHelper.addTag(tag);
    }

    @Override
    public Completable deleteTag(Tag tag) {
        return dbHelper.deleteTag(tag);
    }

    @Override
    public Completable updateTag(Tag tag) {
        return dbHelper.updateTag(tag);
    }

    /**
     * Trash
     */
    @Override
    public Observable<List<TrashNote>> getTrashNotesLoad() {
        return dbHelper.getTrashNotesLoad();
    }

    @Override
    public Completable moveToTrash(Note note) {
        return dbHelper.moveToTrash(note);
    }

    @Override
    public Completable moveToTrash(ArrayList<Note> notes) {
        return dbHelper.moveToTrash(notes);
    }

    @Override
    public Completable deleteNote(TrashNote note) {
        return dbHelper.deleteNote(note);
    }

    @Override
    public Completable deleteNote(ArrayList<TrashNote> notes) {
        return dbHelper.deleteNote(notes);
    }

    @Override
    public Completable deleteAll() {
        return dbHelper.deleteAll();
    }



}
