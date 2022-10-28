package com.pasich.mynotes.data.database.helpers;

import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.data.old.notes.Note;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DbTrashHelper {
    /**
     * Trash
     */
    Observable<List<TrashNote>> getTrashNotesLoad();

    Completable moveToTrash(Note note);

    Completable moveToTrash(ArrayList<Note> notes);

    Completable deleteNote(TrashNote note);

    Completable deleteNote(ArrayList<TrashNote> notes);

    Completable deleteAll();
}
