package com.pasich.mynotes.data.notes.source;


import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.dao.NoteDao;
import com.pasich.mynotes.data.trash.TrashNote;
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
    private final Executor executor;
    private final NoteDao noteDao;

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

    public List<?> getNotesAll() throws ExecutionException, InterruptedException {
        Future<?> future = Executors.newSingleThreadExecutor()
                .submit((Callable<?>) noteDao::getNotesAll);
        return (List<?>) future.get();
    }


    public long addNote(Note note) throws ExecutionException, InterruptedException {
        Future<?> future = Executors.newSingleThreadExecutor()
                .submit((Callable<?>) () -> noteDao.addNote(note));
        return (long) future.get();
    }

    public void deleteNote(Note note) {
        executor.execute(() -> noteDao.deleteNote(note));
    }

    public void deleteNote(ArrayList<Note> notes) {
        for (Note note : notes)
            executor.execute(() -> noteDao.deleteNote(note));
    }

    public void updateNote(Note note) {
        executor.execute(() -> noteDao.updateNote(note));
    }


    public ArrayList<Note> getNotesFromTag(String nameTag) throws ExecutionException, InterruptedException {
        Future<?> future = Executors.newSingleThreadExecutor()
                .submit((Callable<?>) () -> noteDao.getNotesForTag(nameTag));
        return (ArrayList<Note>) future.get();
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

    public void moveToNotes(ArrayList<TrashNote> notes) {
        for (TrashNote note : notes)
            executor.execute(() -> noteDao.moveToTrash(note.getTitle(), note.getValue(), note.getDate()));
    }

}
