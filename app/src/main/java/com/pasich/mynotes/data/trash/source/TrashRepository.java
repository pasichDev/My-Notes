package com.pasich.mynotes.data.trash.source;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.trash.TrashNote;
import com.pasich.mynotes.data.trash.source.dao.TrashDao;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class TrashRepository {

    private static TrashRepository instance;
    private final Executor executor;
    private final TrashDao trashDao;

    private TrashRepository(Executor executor, TrashDao trashDao) {
        this.executor = executor;
        this.trashDao = trashDao;
    }

    public static TrashRepository getInstance(TrashDao trashDao) {
        if (instance == null) {
            instance = new TrashRepository(new DiskExecutor(), trashDao);
        }
        return instance;
    }

    public LiveData<List<TrashNote>> getNotes() {
        return trashDao.getTrash();
    }

    public void moveToTrash(Note note) {
        executor.execute(() -> trashDao.moveToTrash(new TrashNote().create(note.getTitle(), note.getValue(), note.getDate())));
    }

    public void moveToTrash(ArrayList<Note> notes) {
        for (Note note : notes)
            executor.execute(() -> trashDao.moveToTrash(new TrashNote().create(note.getTitle(), note.getValue(), note.getDate())));
    }


    public void deleteNote(TrashNote note) {
        executor.execute(() -> trashDao.deleteNote(note));
    }

    public void deleteNote(ArrayList<TrashNote> notes) {
        for (TrashNote note : notes)
            executor.execute(() -> trashDao.deleteNote(note));
    }

    public void deleteAll() {
        executor.execute(trashDao::deleteAll);
    }

}
