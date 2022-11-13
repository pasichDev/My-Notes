package com.pasich.mynotes.ui.presenter;


import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.ui.contract.HelloContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class HelloPresenter extends AppBasePresenter<HelloContract.view> implements HelloContract.presenter {


    @Inject
    public HelloPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().initListeners();
    }

    @Override
    public void addNote(Note note) {
        getCompositeDisposable().add(getDataManager().addNote(note).subscribeOn(getSchedulerProvider().io()).subscribe());
    }

    @Override
    public void addTrashNote(TrashNote note) {
        getCompositeDisposable().add(getDataManager().addTrashNote(note).subscribeOn(getSchedulerProvider().io()).subscribe());

    }

}
