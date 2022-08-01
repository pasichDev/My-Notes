package com.pasich.mynotes.data.trash.source.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pasich.mynotes.data.trash.TrashNote;

import java.util.List;

@Dao
public interface TrashDao {
  @Query("SELECT * FROM trash")
  LiveData<List<TrashNote>> getTrash();


  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void moveToTrash(TrashNote note);


  @Delete
  void deleteNote(TrashNote note);

  @Query("DELETE FROM trash")
  void deleteAll();

}
