package com.pasich.mynotes.data.database.helpers;

import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.data.model.Tag;
import com.pasich.mynotes.data.model.TrashNote;

import java.util.List;

import io.reactivex.Completable;

public interface DbTransactionsHelper {

    Completable moveNoteToTrash(TrashNote tNote, Note mNote);

    Completable deleteTagForNotes(Tag tag);

    Completable deleteTagAndNotes(Tag tag);

    Completable transferNoteOutTrash(TrashNote tNote, Note mNote);

    Completable restoreNote(Note mNote);

    Completable renameTag(Tag mTag, String newName);

    Completable updateListNotes(List<ItemListNote> updateList, List<ItemListNote> deleteList);

}
