package com.pasich.mynotes.ui.presenter.dialogs;


import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.ui.contract.dialogs.MoreNoteDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MoreNoteDialogPresenter extends AppBasePresenter<MoreNoteDialogContract.view> implements MoreNoteDialogContract.presenter {


    @Inject
    public MoreNoteDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void viewIsReady() {
        getView().initInterfaces();
        getView().loadingTagsOfChips(getDataManager().getTagsUser());
        getView().initListeners();
        getView().setSeekBarValue(getDataManager().getSizeTextNoteActivity());
    }


    @Override
    public void deleteNote(Note note) {
        getCompositeDisposable().add(
                getDataManager().moveNoteToTrash(new TrashNote().create(note.getTitle(), note.getValue(), note.getDate()), note)
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe());
    }

    @Override
    public void editSizeText(int value) {
        getDataManager().editSizeTextNoteActivity(value);
    }

    @Override
    public void removeTagNote(int idNote) {
        getCompositeDisposable().add(getDataManager().setTagNote("", idNote).subscribeOn(getSchedulerProvider().io()).subscribe());
    }

    @Override
    public void editTagNote(String nameTag, int idNote) {
        getCompositeDisposable().add(getDataManager().setTagNote(nameTag, idNote).subscribeOn(getSchedulerProvider().io()).subscribe());
    }
}
