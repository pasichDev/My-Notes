package com.pasich.mynotes.data.trash.source;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.trash.TrashNote;
import com.pasich.mynotes.data.trash.source.dao.TrashDao;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TrashRepository {

    private static TrashRepository instance;
    private Executor executor;
    private TrashDao trashDao;

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
        Runnable runnable = () -> {
            trashDao.moveToTrash(note.getTitle(), note.getValue(), note.getDate());

        };
        Executors.newSingleThreadExecutor().execute(runnable);
    }

    public void deleteNote(TrashNote note) {
        Runnable runnable = () -> trashDao.deleteNote(note);
        executor.execute(runnable);
    }

    public void deleteAll() {
        Runnable runnable = () -> trashDao.deleteAll();
        executor.execute(runnable);
    }

    public void destroyInstance() {
        instance = null;
        trashDao = null;
        executor = null;
    }
}
