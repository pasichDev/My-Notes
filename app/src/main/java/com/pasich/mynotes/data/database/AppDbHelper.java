package com.pasich.mynotes.data.database;


import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase appDatabase;

    @Inject
    AppDbHelper(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }


    @Override
    public Flowable<List<Tag>> getTags() {
        return appDatabase.tagsDao().getTags();
    }

    @Override
    public Observable<List<Tag>> getTagsUser() {
        return Observable.fromCallable(() -> appDatabase.tagsDao().getTagsUser());
    }

    @Override
    public Single<Integer> getCountTagAll() {
        return Single.fromCallable(() -> appDatabase.tagsDao().getCountAllTag());
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
    public Flowable<List<Note>> getNotes() {
        return appDatabase.noteDao().getNotesAll();
    }

    @Override
    public Observable<List<Note>> getNotesForTag(String nameTag) {
        return Observable.fromCallable(() -> appDatabase.noteDao().getNotesForTag(nameTag));
    }

    @Override
    public Single<Integer> getCountNotesTag(String nameTag) {
        return Single.fromCallable(() -> appDatabase.noteDao().getCountNotesTag(nameTag));
    }

    @Override
    public Single<Note> getNoteForId(int idNote) {
        return Single.fromCallable(() -> appDatabase.noteDao().getNoteForId(idNote));
    }

    @Override
    public Observable<Long> addNote(Note note) {
        return Observable.fromCallable(() -> appDatabase.noteDao().addNote(note));
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
