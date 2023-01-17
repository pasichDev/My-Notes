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
    public Preference getBackupCloudInfoPreference() {
        return preferencesHelper.getBackupCloudInfoPreference();
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
    public long getLastDataBackupCloud() {
        return preferencesHelper.getLastDataBackupCloud();
    }

    @Override
    public String getLastBackupCloudId() {
        return preferencesHelper.getLastBackupCloudId();
    }

    @Override
    public void editSizeTextNoteActivity(int value) {
        preferencesHelper.editSizeTextNoteActivity(value);
    }

    @Override
    public int getSetCloudAuthBackup() {
        return preferencesHelper.getSetCloudAuthBackup();
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
    public Flowable<List<Tag>> getTagsUser() {
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
    public Flowable<List<TrashNote>> getTrashNotesLoad() {
        return dbHelper.getTrashNotesLoad();
    }


    @Override
    public Completable deleteTrashNotes(List<TrashNote> note) {
        return dbHelper.deleteTrashNotes(note);
    }


    @Override
    public Completable deleteAll() {
        return dbHelper.deleteAll();
    }

    @Override
    public Completable addTrashNote(TrashNote note) {
        return dbHelper.addTrashNote(note);
    }

    @Override
    public Completable moveNoteToTrash(TrashNote tNote, Note mNote) {
        return dbHelper.moveNoteToTrash(tNote, mNote);
    }

    @Override
    public Completable deleteTagForNotes(Tag tag) {
        return dbHelper.deleteTagForNotes(tag);
    }

    @Override
    public Completable deleteTagAndNotes(Tag tag) {
        return dbHelper.deleteTagAndNotes(tag);
    }

    @Override
    public Completable transferNoteOutTrash(TrashNote tNote, Note mNote) {
        return dbHelper.transferNoteOutTrash(tNote, mNote);
    }

    @Override
    public Completable restoreNote(Note mNote) {
        return dbHelper.restoreNote(mNote);
    }

    @Override
    public Completable renameTag(Tag mTag, String newName) {
        return dbHelper.renameTag(mTag, newName);
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
    public Single<Note> getNoteForId(long idNote) {
        return dbHelper.getNoteForId(idNote);
    }


    @Override
    public Single<Long> addNote(Note note, boolean copyNote) {
        return dbHelper.addNote(note, copyNote);
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
    public Completable moveToNotes(List<Note> notes) {
        return dbHelper.moveToNotes(notes);
    }

    @Override
    public Completable setTagNote(String nameTag, int idNote) {
        return dbHelper.setTagNote(nameTag, idNote);
    }

}
