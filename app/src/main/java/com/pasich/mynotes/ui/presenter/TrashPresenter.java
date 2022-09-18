package com.pasich.mynotes.ui.presenter;


import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.trash.TrashNote;
import com.pasich.mynotes.data.trash.source.TrashRepository;
import com.pasich.mynotes.ui.contract.TrashContract;

import java.util.ArrayList;


public class TrashPresenter extends PresenterBase<TrashContract.view>
        implements TrashContract.presenter {

    private TrashRepository trashRepository;
    private NotesRepository notesRepository;

    public TrashPresenter() {
    }

    @Override
    public void setDataManager(DataManager dataManager) {
        trashRepository = dataManager.getTrashRepository();
        notesRepository = dataManager.getNotesRepository();
    }

    @Override
    public void viewIsReady() {
        getView().settingsActionBar();
        getView().settingsNotesList(1, trashRepository.getNotes());
        getView().initListeners();
        getView().initActionUtils();
    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {

    }


    @Override
    public void cleanTrashDialogStart() {
        if (isViewAttached()) getView().cleanTrashDialogShow();
    }

    @Override
    public void restoreNotesArray(ArrayList<TrashNote> notes) {
        if (trashRepository != null) {
            notesRepository.moveToNotes(notes);
            trashRepository.deleteNote(notes);
        }
    }

}
