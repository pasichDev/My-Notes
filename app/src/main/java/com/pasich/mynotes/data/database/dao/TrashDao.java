package com.pasich.mynotes.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pasich.mynotes.data.database.model.TrashNote;

import java.util.List;

@Dao
public interface TrashDao {
  @Query("SELECT * FROM trash")
  List<TrashNote> getTrash();


  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void moveToTrash(TrashNote note);

  @Delete
  void deleteNote(TrashNote note);

  @Query("DELETE FROM trash")
  void deleteAll();

}
