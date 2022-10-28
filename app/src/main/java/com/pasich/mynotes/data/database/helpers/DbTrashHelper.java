package com.pasich.mynotes.data.database.helpers;

import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.TrashNote;

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

    Completable deleteTrashNote(TrashNote note);

    Completable deleteTrashNote(ArrayList<TrashNote> notes);

    Completable deleteAll();
}
