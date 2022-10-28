package com.pasich.mynotes.ui.presenter;


import com.pasich.mynotes.base.activity.BasePresenterActivity;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.ui.contract.TrashContract;

import java.util.ArrayList;


public class TrashPresenter extends BasePresenterActivity<TrashContract.view>
        implements TrashContract.presenter {


    public TrashPresenter(DataManager dataManager) {
        super(dataManager);

    }



    @Override
    public void viewIsReady() {
        getView().settingsActionBar();
        getView().settingsNotesList(1, null);
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
        //  if (trashRepository != null && notesRepository != null) {
        //       notesRepository.moveToNotes(notes);
        //       trashRepository.deleteNote(notes);
        //    }
    }

}
