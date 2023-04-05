package com.pasich.mynotes.data.database.helpers;


import com.pasich.mynotes.data.model.DataNote;

import java.util.List;

import io.reactivex.Flowable;

public interface DataNoteHelper {

    Flowable<List<DataNote>> getDataNotes();
}
