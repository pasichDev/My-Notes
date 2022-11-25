package com.pasich.mynotes.ui.presenter.dialogs;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.ui.contract.dialogs.NewTagDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class NewTagDialogPresenter extends AppBasePresenter<NewTagDialogContract.view> implements NewTagDialogContract.presenter {

    @Inject
    public NewTagDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().initListeners();
    }


    @Override
    public void saveTag(String nameNewTag) {
        getCompositeDisposable().add(getDataManager().addTag(new Tag().create(nameNewTag))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe());
    }


}
