package com.pasich.mynotes.ui.presenter;


import android.util.Log;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class TrashPresenter extends AppBasePresenter<TrashContract.view>
        implements TrashContract.presenter {


    @Inject
    public TrashPresenter(SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable,
                          DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }


    @Override
    public void viewIsReady() {
        getView().settingsActionBar();
        getView().settingsNotesList(getDataManager().getTrashNotesLoad());
        getView().initListeners();
        getView().initActionUtils();
    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {

    }


    @Override
    public void cleanTrashDialogStart() {
        if (isViewAttached()) getView().cleanTrashDialogShow();
    }

    @Override
    public void restoreNotesArray(ArrayList<TrashNote> notes) {

        List<Note> newNotes = new ArrayList<>();
        for (TrashNote note : notes) {
            newNotes.add(new Note().create(note.getTitle(), note.getValue(), note.getDate()));
        }

        if (newNotes.size() > 0) {
            getDataManager().moveToNotes(newNotes)
                    .subscribeOn(getSchedulerProvider().io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            getCompositeDisposable().add(d);
                        }

                        @Override
                        public void onComplete() {
                            Log.wtf("pasic", "onComplete: ");
                            getDataManager().deleteTrashNotes(notes)
                                    .subscribeOn(AndroidSchedulers.mainThread())
                                    .subscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.wtf("pasic", "onError: " + e);
                        }
                    });
        }


    }

}
