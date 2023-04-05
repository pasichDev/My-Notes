package com.pasich.mynotes.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.pasich.mynotes.data.model.DataNote;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DataNoteDao {
    @Transaction
    @Query("SELECT * FROM notes")
    Flowable<List<DataNote>> getDataNotes();
}
