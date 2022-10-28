package com.pasich.mynotes.ui.presenter;


import com.pasich.mynotes.base.activity.BasePresenterActivity;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.ui.contract.NoteContract;

import java.util.concurrent.ExecutionException;

public class NotePresenter extends BasePresenterActivity<NoteContract.view>
        implements NoteContract.presenter {


    public NotePresenter(DataManager dataManager) {
        super(dataManager);
    }



    @Override
    public void viewIsReady() {
        getView().changeTextStyle();
        getView().changeTextSizeOffline();
        getView().settingsActionBar();
        getView().initTypeActivity();
        getView().createSpeechRecognizer();
        getView().initListeners();
        getView().initListenerSpeechRecognizer();
        getView().createActionPanelNote();
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
