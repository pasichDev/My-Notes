package com.pasich.mynotes.ui.presenter.dialogs;


import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.ui.contract.dialogs.ChoiceNoteDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ChoiceNoteDialogPresenter extends AppBasePresenter<ChoiceNoteDialogContract.view> implements ChoiceNoteDialogContract.presenter {


    @Inject
    public ChoiceNoteDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void viewIsReady() {
        getView().initListeners();
    }


    @Override
    public void deleteNote(Note note) {
        getCompositeDisposable().add(
                getDataManager().deleteNote(note)
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe()
        );
    }
}
