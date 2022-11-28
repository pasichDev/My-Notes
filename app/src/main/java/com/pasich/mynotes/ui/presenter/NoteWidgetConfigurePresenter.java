package com.pasich.mynotes.ui.presenter;


import android.util.Log;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.ui.contract.NoteWidgetConfigureContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class NoteWidgetConfigurePresenter extends AppBasePresenter<NoteWidgetConfigureContract.view> implements NoteWidgetConfigureContract.presenter {


    @Inject
    public NoteWidgetConfigurePresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().initListNotes();
        loadNotes();
        getView().initListeners();
    }


    @Override
    public void loadNotes() {
        getCompositeDisposable().add(getDataManager().getNotes().subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe((noteList) -> getView().loadingNotes(noteList), throwable -> Log.e("com.pasich.myNotes", "loadNotes", throwable)));
    }


}
