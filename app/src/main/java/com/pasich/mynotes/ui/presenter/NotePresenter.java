package com.pasich.mynotes.ui.presenter;


import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class NotePresenter extends AppBasePresenter<NoteContract.view> implements NoteContract.presenter {


    @Inject
    public NotePresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }


    @Override
    public void viewIsReady() {
        getView().changeTextStyle();
        getView().changeTextSizeOffline();
        getView().settingsActionBar();
        getView().initTypeActivity();
        getView().initListeners();
        getView().initListenerSpeechRecognizer();
    }


    @Override
    public void detachView() {
    }

    @Override
    public void destroy() {
    }


    @Override
    public void closeActivity() {
        getView().closeNoteActivity();

    }

    @Override
    public void loadingData(int idNote) {
        getCompositeDisposable().add(getDataManager().getNoteForId(idNote).subscribeOn(getSchedulerProvider().io()).subscribe(note -> getView().loadingNote(note)));

    }

    @Override
    public void activateEditNote() {
        getView().activatedActivity();
    }

    @Override
    public void createNote(Note note) {
        getCompositeDisposable().add(getDataManager().addNote(note)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(aLong -> getView().editIdNoteCreated(aLong)));
    }

    @Override
    public void saveNote(Note note) {
        getCompositeDisposable().add(getDataManager().updateNote(note).subscribeOn(getSchedulerProvider().io()).subscribe());
    }

    @Override
    public void deleteNote(Note note) {
        getCompositeDisposable().add(getDataManager().deleteNote(note).subscribeOn(getSchedulerProvider().io()).subscribe());
    }

    @Override
    public void sourceNote() {
        getView().loadingSourceNote();
    }

}
