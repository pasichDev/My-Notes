package com.pasich.mynotes.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pasich.mynotes.data.database.model.Note;

import java.util.List;

@Dao
public interface NoteDao {


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


  @Query("UPDATE NOTES SET tag=:tag WHERE id=:noteID")
  void setTagNote(String tag, int noteID);


}