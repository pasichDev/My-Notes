package com.pasich.mynotes.data.database.helpers;


import com.pasich.mynotes.data.model.ItemListNote;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DbItemListNoteHelper {
    Observable<List<ItemListNote>> getListForIdNote(long idNote);

    Completable deleteItemsList(int idNote);
}
