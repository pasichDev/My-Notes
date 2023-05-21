package com.pasich.mynotes.ui.presenter;


import android.content.Intent;
import android.graphics.Typeface;

import com.pasich.mynotes.base.presenter.BasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.state.NoteState;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class NotePresenter extends BasePresenter<NoteContract.view> implements NoteContract.presenter {

    @Inject
    public NoteState noteState;


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

    }


    @Override
    public void closeActivity() {
        getView().closeNoteActivity();

    }

    @Override
    public void getLoadIntentData(Intent mIntent) {
        noteState.setIdKey(mIntent.getLongExtra("idNote", 0));
        noteState.setTagNote(mIntent.getStringExtra("tagNote"));
        noteState.setShareText(mIntent.getStringExtra("shareText"));
        noteState.setNewNoteKey(mIntent.getBooleanExtra("NewNote", true));
    }

    @Override
    public void loadingData(long idNote) {
        getCompositeDisposable().add(getDataManager().getNoteForId(idNote).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(note -> {
            getView().loadingNote(note);
            noteState.setNote(note);
        }));
        getCompositeDisposable().add(getDataManager().getListForIdNote(idNote).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(listNotes -> {
            getView().loadingListNote(listNotes);
            noteState.setListNotesItems(listNotes);
        }));

    }

    @Override
    public void activateEditNote() {
        getView().activatedActivity();
    }

    @Override
    public void createNote(Note note) {
        getCompositeDisposable().add(getDataManager().addNote(note, false).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(aLong -> getView().editIdNoteCreated(aLong)));
    }

    @Override
    public void saveNote(Note note) {
        getCompositeDisposable().add(getDataManager().updateNote(note).subscribeOn(getSchedulerProvider().io()).subscribe());
    }

    @Override
    public void saveItemList(List<ItemListNote> updateList, List<ItemListNote> deleteList) {
        getCompositeDisposable().add(getDataManager().updateListNotes(updateList, deleteList).subscribeOn(getSchedulerProvider().io()).subscribe());

    }

    @Override
    public void deleteNote(Note note) {
        getCompositeDisposable().add(getDataManager().deleteNote(note).subscribeOn(getSchedulerProvider().io()).subscribe());
    }


    @Override
    public int getTypeFace(String textStyle) {
        return switch (textStyle) {
            case "italic" -> Typeface.ITALIC;
            case "bold" -> Typeface.BOLD;
            case "bold-italic" -> Typeface.BOLD_ITALIC;
            default -> Typeface.NORMAL;
        };
    }

    @Override
    public NoteState getNoteState() {
        return noteState;
    }


    @Override
    public void deleteList(int idNote) {
        getCompositeDisposable().add(getDataManager().deleteItemsList(idNote).subscribeOn(getSchedulerProvider().io()).subscribe());

    }
}
