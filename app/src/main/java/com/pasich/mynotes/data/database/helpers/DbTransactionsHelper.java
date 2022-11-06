package com.pasich.mynotes.data.database.helpers;

import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;

import io.reactivex.Completable;

public interface DbTransactionsHelper {

    Completable moveNoteToTrash(TrashNote tNote, Note mNote);

    Completable deleteTagForNotes(Tag tag);

    Completable deleteTagAndNotes(Tag tag);

    Completable transferNoteOutTrash(TrashNote tNote, Note mNote);

}
