package com.pasich.mynotes.ui.presenter.dialogs;


import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.ui.contract.dialogs.ChoiceTagDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ChoiceTagDialogPresenter extends AppBasePresenter<ChoiceTagDialogContract.view> implements ChoiceTagDialogContract.presenter {

    private int countNotesForTag;

    @Inject
    public ChoiceTagDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }


    @Override
    public void detachView() {
        super.detachView();

        countNotesForTag = 0;
    }

    @Override
    public void viewIsReady() {
        getView().initListeners();
    }


    @Override
    public void getLoadCountNotesForTag(String nameTag) {
        getCompositeDisposable().add(getDataManager().getCountNotesTag(nameTag).subscribeOn(getSchedulerProvider().io()).subscribe(integer -> countNotesForTag = integer));

    }

    @Override
    public void editVisibilityTag(Tag tag) {
        getCompositeDisposable().add(getDataManager().updateTag(tag).subscribeOn(getSchedulerProvider().io()).subscribe());
    }

    @Override
    public void deleteTagInitial(Tag tag) {
        if (getCountNotesForTag() == 0)
            getCompositeDisposable().add(getDataManager().deleteTag(tag).subscribeOn(getSchedulerProvider().io()).subscribe());
        else getView().startDeleteTagDialog();
    }

    public int getCountNotesForTag() {
        return countNotesForTag;
    }
}
