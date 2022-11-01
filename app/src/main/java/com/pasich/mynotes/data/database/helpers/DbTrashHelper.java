package com.pasich.mynotes.data.database.helpers;

import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.TrashNote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface DbTrashHelper {
    /**
     * Trash
     */
    Flowable<List<TrashNote>> getTrashNotesLoad();

    Completable moveToTrash(Note note);

    Completable moveToTrash(ArrayList<Note> notes);

    Completable deleteTrashNotes(List<TrashNote> note);

    Completable deleteAll();
}
