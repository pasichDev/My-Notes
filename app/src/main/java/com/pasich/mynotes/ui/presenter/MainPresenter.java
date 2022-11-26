package com.pasich.mynotes.ui.presenter;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import android.util.Log;
import android.view.View;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class MainPresenter extends AppBasePresenter<MainContract.view> implements MainContract.presenter {


    @Inject
    public MainPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().settingsSearchView();
        getView().settingsTagsList();
        getView().settingsNotesList();
        loadingData();
        getView().initListeners();
    }

    @Override
    public void loadingData() {
        getCompositeDisposable().add(getDataManager().getTags().subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe((tagList) -> getView().loadingTags(tagList), throwable -> Log.e("com.pasich.myNotes", "loadTags", throwable)));
        getCompositeDisposable().add(getDataManager().getNotes().subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe((noteList) -> getView().loadingNotes(noteList), throwable -> Log.e("com.pasich.myNotes", "loadNotes", throwable)));
    }


    @Override
    public void newNotesClick() {
        if (isViewAttached()) getView().newNotesButton();
    }

    @Override
    public void moreActivityClick() {
        if (isViewAttached()) getView().moreActivity();
    }


    @Override
    public void clickTag(Tag tag, int position) {
        if (tag.getSystemAction() == 1) {
            getCompositeDisposable().add(getDataManager().getCountTagAll().subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(integer -> {
                if (integer >= MAX_TAG_COUNT) {
                    getView().startToastCheckCountTags();
                } else getView().startCreateTagDialog();
            }));


        } else {
            getView().selectTagUser(position);

        }
    }


    @Override
    public void clickLongTag(Tag tag, View mView) {
        if (tag.getSystemAction() == 0) {
            getView().choiceTagDialog(tag, mView);
        }
    }

    @Override
    public void clickNote(int idNote) {
        getView().openNoteEdit(idNote);
    }


    @Override
    public void deleteNotesArray(ArrayList<Note> notes) {
        for (Note note : notes) {
            getCompositeDisposable().add(getDataManager().moveNoteToTrash(new TrashNote().create(note.getTitle(), note.getValue(), note.getDate()), note).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe());
        }
    }

    @Override
    public void deleteNote(Note note) {
        getCompositeDisposable().add(getDataManager().moveNoteToTrash(new TrashNote().create(note.getTitle(), note.getValue(), note.getDate()), note).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe());
    }

    @Override
    public void restoreNote(Note nNote) {
        getCompositeDisposable().add(getDataManager().restoreNote(nNote).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe());
    }

    @Override
    public void deleteTag(Tag tag) {
        getCompositeDisposable().add(getDataManager()
                .getCountNotesTag(tag.getNameTag())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(integer -> {
                    if (integer == 0)
                        getCompositeDisposable().add(getDataManager().deleteTag(tag).subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui()).subscribe());
                    else getView().startDeleteTagDialog(tag);
                }));

    }


    @Override
    public String getSortParam() {
        return getDataManager().getSortParam();
    }

    @Override
    @Deprecated
    public void addNote(Note note) {
        getCompositeDisposable().add(getDataManager().addNote(note, false).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe());
    }

    @Override
    public void sortButton() {
        getView().sortButton();
    }

    @Override
    public void formatButton() {
        getView().formatButton();
    }

    @Override
    public void startSearchDialog() {
        getView().startSearchDialog();
    }


}
