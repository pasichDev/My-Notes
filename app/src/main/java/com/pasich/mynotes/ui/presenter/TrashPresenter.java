package com.pasich.mynotes.ui.presenter;


import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.trash.source.TrashRepository;
import com.pasich.mynotes.ui.contract.TrashContract;


public class TrashPresenter extends PresenterBase<TrashContract.view>
        implements TrashContract.presenter {

    private DataManager data;
    private TrashRepository trashRepository;

    public TrashPresenter() {
    }

    @Override
    public void setDataManager(DataManager dataManager) {
        data = dataManager;
        trashRepository = data.getTrashRepository();
    }

    @Override
    public void viewIsReady() {
        getView().settingsActionBar();
        getView().settingsNotesList(1, trashRepository.getNotes());
        getView().initListeners();
    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {
        trashRepository.destroyInstance();
        data = null;
    }


    @Override
    public void cleanTrashDialogStart() {
        if (isViewAttached()) getView().cleanTrashDialogShow();
    }

}
