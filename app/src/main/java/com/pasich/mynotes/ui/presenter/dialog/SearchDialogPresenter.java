package com.pasich.mynotes.ui.presenter.dialog;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.ui.contract.dialog.SearchDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;


public class SearchDialogPresenter extends AppBasePresenter<SearchDialogContract.view>
        implements SearchDialogContract.presenter {


    public SearchDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().initFabButton();
        getView().settingsListResult();
      /*  try {
            getView().createListenerSearch(notesRepository.getNotes());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

       */

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
