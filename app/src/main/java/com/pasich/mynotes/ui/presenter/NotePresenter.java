package com.pasich.mynotes.ui.presenter;


import com.pasich.mynotes.base.activity.BasePresenterActivity;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.utils.SchedulerProvider.SchedulerProvider;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class NotePresenter extends BasePresenterActivity<NoteContract.view>
        implements NoteContract.presenter {


    @Inject
    public NotePresenter(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }



    @Override
    public void viewIsReady() {
        getView().changeTextStyle();
        getView().changeTextSizeOffline();
        getView().settingsActionBar();
        getView().initTypeActivity();
        getView().initListeners();
        getView().initListenerSpeechRecognizer();
    }


    @Override
    public void detachView() {
    }

    @Override
    public void destroy() {
    }


    @Override
    public void closeActivity() {
        getView().closeNoteActivity();

    }

    @Override
    public void loadingData(int idNote) {
        //     getView().loadingNote(notesRepository.getNoteFromId(idNote));

    }

    @Override
    public void activateEditNote() {
        getView().activatedActivity();
    }

    @Override
    public long createNote(Note note) {
        //  return notesRepository.addNote(note);
        return 0;
    }

    @Override
    public void saveNote(Note note) {
        //   notesRepository.updateNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        synchronized (this) {
            /// data.getTrashRepository().moveToTrash(note);
        }
        // notesRepository.deleteNote(note);
    }

    @Override
    public void sourceNote() {
        getView().loadingSourceNote();
    }


    @Override
    public Note loadingNote(int idNote) throws ExecutionException, InterruptedException {
        //   return notesRepository.getNoteFromId(idNote);
        return null;
    }
}
