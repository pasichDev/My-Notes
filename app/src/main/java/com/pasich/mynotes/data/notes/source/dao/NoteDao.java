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

  @Query("SELECT * FROM notes")
  List<Note> getNotesAll();

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  long addNote(Note note);

  @Update
  void updateNote(Note note);

  @Delete
  void deleteNote(Note note);

  @Query("SELECT COUNT(tag) FROM notes WHERE tag = :nameTag")
  int getCountNotesTag(String nameTag);

  @Query("SELECT * FROM notes WHERE tag = :nameTag")
  List<Note> getNotesForTag(String nameTag);

  @Query("SELECT * FROM notes WHERE id=:idNote")
  Note getNoteForId(int idNote);

  @Query("INSERT INTO notes (title,value,date, tag) VALUES (:title,:value,:date, '')")
  void moveToTrash(String title, String value, long date);
}
