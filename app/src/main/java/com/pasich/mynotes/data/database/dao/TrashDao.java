package com.pasich.mynotes.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.pasich.mynotes.data.database.model.TrashNote;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TrashDao {
  @Query("SELECT * FROM trash")
  Flowable<List<TrashNote>> getTrash();

  @Delete
  void deleteNote(TrashNote note);

  @Query("DELETE FROM trash")
  void deleteAll();

}
