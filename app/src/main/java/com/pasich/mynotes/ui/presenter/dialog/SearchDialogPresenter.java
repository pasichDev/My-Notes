package com.pasich.mynotes.ui.presenter.dialog;


import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.ui.contract.dialog.SearchDialogContract;

import java.util.concurrent.ExecutionException;


public class SearchDialogPresenter extends PresenterBase<SearchDialogContract.view>
        implements SearchDialogContract.presenter {

    private TagsRepository tagsRepository;
    private NotesRepository notesRepository;

    public SearchDialogPresenter() {
    }

    @Override
    public void setDataManager(DataManager dataManager) {
        tagsRepository = dataManager.getTagsRepository();
        notesRepository = dataManager.getNotesRepository();
    }

    @Override
    public void viewIsReady() {

        getView().initListeners();
        getView().settingsListResult();
        try {
            getView().createListenerSearch(notesRepository.getNotes());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {

    }


}
