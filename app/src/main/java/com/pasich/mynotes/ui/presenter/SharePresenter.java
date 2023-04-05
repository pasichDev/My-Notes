package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.presenter.BasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.ui.contract.ShareContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SharePresenter extends BasePresenter<ShareContract.view> implements ShareContract.presenter {


    @Inject
    public SharePresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }


    @Override
    public void viewIsReady() {
    }


    @Override
    public void creteNoteShare(String textNote) {
        getCompositeDisposable().add(getDataManager().addNote(new Note().create("", textNote, new Date().getTime()), false)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(idNewNote -> {
                    if (isViewAttached()) getView().openNoteEdition(idNewNote);
                }));

    }
}
