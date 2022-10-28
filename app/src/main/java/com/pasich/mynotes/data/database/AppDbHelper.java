package com.pasich.mynotes.data.database;


import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.data.old.notes.Note;

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
    public Completable deleteNote(TrashNote note) {
        return Completable.fromAction(() -> appDatabase.trashDao().deleteNote(note));
    }

    @Override
    public Completable deleteNote(ArrayList<TrashNote> notes) {
        return Completable.fromRunnable(() -> {
            for (TrashNote note : notes)
                appDatabase.trashDao().deleteNote(note);
        });
    }

    @Override
    public Completable deleteAll() {
        return Completable.fromAction(() -> appDatabase.trashDao().deleteAll());
    }
}
