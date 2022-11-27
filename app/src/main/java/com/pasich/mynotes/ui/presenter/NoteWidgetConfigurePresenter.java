package com.pasich.mynotes.ui.presenter;


import android.util.Log;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.ui.contract.NoteWidgetConfigureContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


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

    @Override
    public Single loadNote(long idNote) {
        Single single =
                getDataManager()
                        .getNoteForId(idNote)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui());
        getCompositeDisposable().add((Disposable) single);
        return single;

    }
}
