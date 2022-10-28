package com.pasich.mynotes.ui.presenter.dialog;

import com.pasich.mynotes.base.dialog.BasePresenterDialog;
import com.pasich.mynotes.ui.contract.dialog.SearchDialogContract;


public class SearchDialogPresenter extends BasePresenterDialog<SearchDialogContract.view>
        implements SearchDialogContract.presenter {


    public SearchDialogPresenter() {
    }



    @Override
    public void viewIsReady() {
        getView().initFabButton();
        getView().init();
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


}
