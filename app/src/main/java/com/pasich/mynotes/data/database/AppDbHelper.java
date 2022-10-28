package com.pasich.mynotes.data.database;


import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase appDatabase;

    @Inject
    AppDbHelper(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }


    @Override
    public Observable<List<Tag>> getTags() {
        return Observable.fromCallable(() -> appDatabase.tagsDao().getTags());
    }

    @Override
    public Observable<List<Tag>> getTagsUser() {
        return Observable.fromCallable(() -> appDatabase.tagsDao().getTagsUser());
    }

    @Override
    public Observable<Integer> getCountTagAll() {
        return Observable.fromCallable(() -> appDatabase.tagsDao().getCountAllTag());
    }

    @Override
    public Completable addTag(Tag tag) {
        return Completable.fromAction(() -> appDatabase.tagsDao().addTag(tag));
    }

    @Override
    public Completable deleteTag(Tag tag) {
        return Completable.fromAction(() -> appDatabase.tagsDao().deleteTag(tag));
    }

    @Override
    public Completable updateTag(Tag tag) {
        return Completable.fromAction(() -> appDatabase.tagsDao().updateTag(tag));
    }

    /**
     * Trash
     */
    @Override
    public Observable<List<TrashNote>> getTrashNotesLoad() {
        return Observable.fromCallable(() -> appDatabase.trashDao().getTrash());
    }

    @Override
    public Completable moveToTrash(Note note) {
        return Completable.fromAction(() -> appDatabase.trashDao().moveToTrash(new TrashNote().create(note.getTitle(), note.getValue(), note.getDate())));
    }

    @Override
    public Completable moveToTrash(ArrayList<Note> notes) {
        return Completable.fromRunnable(() -> {
            for (Note note : notes)
                appDatabase.trashDao().moveToTrash(new TrashNote().create(note.getTitle(), note.getValue(), note.getDate()));

        });
    }

    @Override
    public Completable deleteTrashNote(TrashNote note) {
        return Completable.fromAction(() -> appDatabase.trashDao().deleteNote(note));
    }

    @Override
    public Completable deleteTrashNote(ArrayList<TrashNote> notes) {
        return Completable.fromRunnable(() -> {
            for (TrashNote note : notes)
                appDatabase.trashDao().deleteNote(note);
        });
    }

    @Override
    public Completable deleteAll() {
        return Completable.fromAction(() -> appDatabase.trashDao().deleteAll());
    }

    /**
     * Notes
     */
    @Override
    public Observable<List<Note>> getNotes() {
        return Observable.fromCallable(() -> appDatabase.noteDao().getNotesAll());
    }

    @Override
    public Observable<List<Note>> getNotesForTag(String nameTag) {
        return Observable.fromCallable(() -> appDatabase.noteDao().getNotesForTag(nameTag));
    }

    @Override
    public Observable<Integer> getCountNotesTag(String nameTag) {
        return Observable.fromCallable(() -> appDatabase.noteDao().getCountNotesTag(nameTag));
    }

    @Override
    public Observable<Note> getNoteForId(int idNote) {
        return Observable.fromCallable(() -> appDatabase.noteDao().getNoteForId(idNote));
    }

    @Override
    public Completable addNote(Note note) {
        return Completable.fromAction(() -> appDatabase.noteDao().addNote(note));
    }

    @Override
    public Completable deleteNote(Note note) {
        return Completable.fromAction(() -> appDatabase.noteDao().deleteNote(note));
    }

    @Override
    public Completable deleteNote(ArrayList<Note> notes) {
        return Completable.fromRunnable(() -> {
            for (Note note : notes)
                appDatabase.noteDao().deleteNote(note);
        });
    }

    @Override
    public Completable updateNote(Note note) {
        return Completable.fromAction(() -> appDatabase.noteDao().updateNote(note));
    }

    @Override
    public Completable moveToNotes(ArrayList<TrashNote> notes) {
        return Completable.fromRunnable(() -> {
            for (TrashNote note : notes)
                appDatabase.noteDao().addNote(new Note().create(note.getTitle(), note.getValue(), note.getDate()));
        });
    }

    @Override
    public Completable setTagNote(String nameTag, int idNote) {
        return Completable.fromAction(() -> appDatabase.noteDao().setTagNote(nameTag, idNote));
    }
}
