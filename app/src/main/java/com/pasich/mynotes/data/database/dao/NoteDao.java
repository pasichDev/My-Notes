package com.pasich.mynotes.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pasich.mynotes.data.database.model.Note;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NoteDao {


  @Query("SELECT * FROM notes")
  Flowable<List<Note>> getNotesAll();

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Long addNote(Note note);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void addNotes(List<Note> notes);

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

  @Query("UPDATE NOTES SET tag='' WHERE tag=:tag")
  void deleteTagForNotes(String tag);

  @Query("DELETE FROM NOTES WHERE tag=:tag")
  void deleteTagAndNotes(String tag);
}
