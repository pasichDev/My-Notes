package com.pasich.mynotes.data.notes.source.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pasich.mynotes.data.notes.Note;

import java.util.List;

@Dao
public interface NoteDao {
  @Query("SELECT * FROM notes")
  LiveData<List<Note>> getNotes();

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void addNote(Note note);

  @Update
  void updateNote(Note note);

  @Delete
  void deleteNote(Note note);

  @Query("SELECT COUNT(tag) FROM notes WHERE tag = :nameTag")
  int getCountNotesTag(String nameTag);


  @Query("DELETE FROM notes WHERE tag = :nameTag")
  void deleteNotesForTag(String nameTag);

  @Query("UPDATE notes Set tag = '' WHERE tag = :nameTag")
  void clearTagForNotes(String nameTag);

  @Query("DELETE FROM notes")
  void deleteAll();
}
