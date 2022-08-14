package com.pasich.mynotes.data.notes.source;


import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.dao.NoteDao;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        return noteDao.getNotes();
    }

    public void addNote(Note note) {

        Runnable runnable = () -> noteDao.addNote(note);
        executor.execute(runnable);
    }

    public void deleteNote(Note note) {
        Runnable runnable = () -> noteDao.deleteNote(note);
        Executors.newSingleThreadExecutor().execute(runnable);
    }

    public void updateNote(Note note) {
        Runnable runnable = () -> noteDao.updateNote(note);
        executor.execute(runnable);
    }

    public void deleteTag(Note note) {
        Runnable runnable = () -> {
            note.setTag("");
            noteDao.updateNote(note);
        };
        executor.execute(runnable);
    }

    public ArrayList<Note> getNotesFromTag(String nameTag) throws ExecutionException, InterruptedException {
        Future<?> future = Executors.newSingleThreadExecutor()
                .submit((Callable<?>) () -> noteDao.getNotesForTag(nameTag));
        return (ArrayList<Note>) future.get();
    }

    public void clearTagForNotes(String nameTag) {
        Runnable runnable = () -> noteDao.clearTagForNotes(nameTag);
        executor.execute(runnable);
    }

    public int getCountNoteTag(String nameTag) throws ExecutionException, InterruptedException {
        Future<?> future = Executors.newSingleThreadExecutor()
                .submit((Callable<?>) () -> noteDao.getCountNotesTag(nameTag));
        return (int) future.get();
    }

    public Note getNoteFromId(int idNote) throws ExecutionException, InterruptedException {
        Future<?> future = Executors.newSingleThreadExecutor()
                .submit((Callable<?>) () -> noteDao.getNoteForId(idNote));
        return (Note) future.get();
    }


}
