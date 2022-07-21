package com.pasich.mynotes.data.notes.source;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.dao.NoteDao;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class NotesRepository {

  private static NotesRepository instance;
  private Executor executor;
  private NoteDao noteDao;

  private NotesRepository(Executor executor, NoteDao noteDao) {
    this.executor = executor;
    this.noteDao = noteDao;
  }

  public static NotesRepository getInstance(NoteDao noteDao) {
    if (instance == null) {
      instance = new NotesRepository(new DiskExecutor(), noteDao);
    }
    return instance;
  }

  public LiveData<List<Note>> getNotes() {
    LiveData<List<Note>> mNotes;
    mNotes = noteDao.getNotes();

    return mNotes;
  }

  public void insert(Note note) {

    Runnable runnable = () -> noteDao.addNote(note);
    executor.execute(runnable);
  }

  public void deleteNote(Note note) {

    Runnable runnable = () -> noteDao.deleteNote(note);
    executor.execute(runnable);
  }

  public void updateNote(Note note) {

    Runnable runnable = () -> noteDao.updateNote(note);
    executor.execute(runnable);
  }

  public void deleteNotesForTag(String nameTag) {
    Runnable runnable = () -> noteDao.deleteNotesForTag(nameTag);
    executor.execute(runnable);
  }

  public void clearTagForNotes(String nameTag) {
    Runnable runnable = () -> noteDao.clearTagForNotes(nameTag);
    executor.execute(runnable);
  }

  public int getCountNoteTag(String nameTag) {
    AtomicInteger count = new AtomicInteger();
    Runnable runnable = () ->{ count.set(noteDao.getCountNotesTag(nameTag));};

    executor.execute(runnable);

    Log.wtf("pasic", "step3: " + count );
    return count.get();
  }

  public void destroyInstance() {
    instance = null;
    noteDao = null;
    executor = null;
  }
}
