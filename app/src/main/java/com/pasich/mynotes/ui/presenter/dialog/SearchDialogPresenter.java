package com.pasich.mynotes.ui.presenter.dialog;

import com.pasich.mynotes.base.dialog.BasePresenterDialog;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.ui.contract.dialog.SearchDialogContract;

import java.util.concurrent.ExecutionException;


public class SearchDialogPresenter extends BasePresenterDialog<SearchDialogContract.view>
        implements SearchDialogContract.presenter {

    private NotesRepository notesRepository;

    public SearchDialogPresenter() {
    }

    @Override
    public void setDataManager(DataManager dataManager) {
        notesRepository = dataManager.getNotesRepository();
    }

    @Override
    public void viewIsReady() {
        getView().initFabButton();
        getView().init();
        getView().settingsListResult();
        try {
            getView().createListenerSearch(notesRepository.getNotes());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        getView().initListeners();
    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {
        notesRepository = null;
    }


}
