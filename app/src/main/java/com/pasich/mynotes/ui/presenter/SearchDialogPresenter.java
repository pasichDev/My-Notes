package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.ui.contract.SearchDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class SearchDialogPresenter extends AppBasePresenter<SearchDialogContract.view> implements SearchDialogContract.presenter {

    @Inject
    public SearchDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().initFabButton();
        getView().settingsListResult();
        getView().createListenerSearch(getDataManager().getNotes());
        getView().initListeners();
    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public DataManager getDataManager() {
        return null;
    }


}
