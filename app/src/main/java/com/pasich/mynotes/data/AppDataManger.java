package com.pasich.mynotes.data;


import com.pasich.mynotes.data.database.DbHelper;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.data.preferences.AppPreferencesHelper;
import com.preference.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManger implements DataManager {


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
    public String getSortParam() {
        return preferencesHelper.getSortParam();
    }

    @Override
    public String getTypeFaceNoteActivity() {
        return preferencesHelper.getTypeFaceNoteActivity();
    }


    /**
     * Tags
     */

    @Override
    public Flowable<List<Tag>> getTags() {
        return dbHelper.getTags();
    }

    @Override
    public Observable<List<Tag>> getTagsUser() {
        return dbHelper.getTagsUser();
    }

    @Override
    public Single<Integer> getCountTagAll() {
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
    public Completable deleteTrashNote(TrashNote note) {
        return dbHelper.deleteTrashNote(note);
    }

    @Override
    public Completable deleteTrashNote(ArrayList<TrashNote> notes) {
        return dbHelper.deleteTrashNote(notes);
    }

    @Override
    public Completable deleteAll() {
        return dbHelper.deleteAll();
    }


    /**
     * Notes
     */
    @Override
    public Flowable<List<Note>> getNotes() {
        return dbHelper.getNotes();
    }

    @Override
    public Observable<List<Note>> getNotesForTag(String nameTag) {
        return dbHelper.getNotesForTag(nameTag);
    }

    @Override
    public Single<Integer> getCountNotesTag(String nameTag) {
        return dbHelper.getCountNotesTag(nameTag);
    }

    @Override
    public Single<Note> getNoteForId(int idNote) {
        return dbHelper.getNoteForId(idNote);
    }

    @Override
    public Observable<Long> addNote(Note note) {
        return dbHelper.addNote(note);
    }

    @Override
    public Completable deleteNote(Note note) {
        return dbHelper.deleteNote(note);
    }

    @Override
    public Completable deleteNote(ArrayList<Note> notes) {
        return dbHelper.deleteNote(notes);
    }

    @Override
    public Completable updateNote(Note note) {
        return dbHelper.updateNote(note);
    }

    @Override
    public Completable moveToNotes(ArrayList<TrashNote> notes) {
        return dbHelper.moveToNotes(notes);
    }

    @Override
    public Completable setTagNote(String nameTag, int idNote) {
        return dbHelper.setTagNote(nameTag, idNote);
    }
}
