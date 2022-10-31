package com.pasich.mynotes.ui.presenter;


import android.util.Log;

import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.ui.contract.ChoiceTagDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ChoiceTagDialogPresenter extends AppBasePresenter<ChoiceTagDialogContract.view> implements ChoiceTagDialogContract.presenter {

    @Inject
    public ChoiceTagDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().initListeners();
    }


    @Override
    public int getLoadCountNotesForTag(String nameTag) {
        final int[] count = {0};
        getCompositeDisposable().add(getDataManager().getCountNotesTag(nameTag).subscribeOn(getSchedulerProvider().io()).subscribe(integer -> count[0] = integer));
        return count[0];
    }

    @Override
    public void editVisibilityTag(Tag tag) {
        getCompositeDisposable().add(getDataManager().updateTag(tag).subscribeOn(getSchedulerProvider().io()).subscribe());
    }

    @Override
    public void deleteTagInitial(Tag tag) {

        Log.wtf("pasic", "deleteTagInitial: " + getLoadCountNotesForTag(tag.getNameTag()));
        if (getLoadCountNotesForTag(tag.getNameTag()) == 0)
            getCompositeDisposable().add(getDataManager().deleteTag(tag).subscribeOn(getSchedulerProvider().io()).subscribe());
        else getView().startDeleteTagDialog();
    }
}
