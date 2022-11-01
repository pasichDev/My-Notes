package com.pasich.mynotes.data.database.helpers;

import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.TrashNote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface DbNotesHelper {
    /**
     * Notes
     */

    Flowable<List<Note>> getNotes();

    Observable<List<Note>> getNotesForTag(String nameTag);

    Single<Integer> getCountNotesTag(String nameTag);

    Single<Note> getNoteForId(int idNote);

    Observable<Long> addNote(Note note);

    Completable deleteNote(Note note);

    Completable deleteNote(ArrayList<Note> notes);

    Completable updateNote(Note note);

    Completable moveToNotes(ArrayList<TrashNote> notes);

    Completable setTagNote(String nameTag, int idNote);

}
