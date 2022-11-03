package com.pasich.mynotes.ui.presenter.dialogs;


import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.ui.contract.dialogs.ClearTrashDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ClearTrashDialogPresenter extends AppBasePresenter<ClearTrashDialogContract.view> implements ClearTrashDialogContract.presenter {


    @Inject
    public ClearTrashDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void viewIsReady() {
        getView().initListeners();
    }


    @Override
    public void clearTrash() {
        getCompositeDisposable().add(getDataManager().deleteAll().subscribeOn(getSchedulerProvider().io()).subscribe());
    }
}
