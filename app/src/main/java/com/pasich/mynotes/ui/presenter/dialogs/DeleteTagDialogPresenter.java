package com.pasich.mynotes.ui.presenter.dialogs;


import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.ui.contract.dialogs.DeleteTagDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DeleteTagDialogPresenter extends AppBasePresenter<DeleteTagDialogContract.view> implements DeleteTagDialogContract.presenter {

    @Inject
    public DeleteTagDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().initListeners();
    }


    @Override
    public int getLoadCountNotesForTag(String nameTag) {
        final int[] count = {0};
        getCompositeDisposable().add(getDataManager()
                .getCountNotesTag(nameTag)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(integer -> count[0] = integer));
        return count[0];
    }


    @Override
    public void deleteTag(Tag tag, boolean deleteNotes) {
        if (getDataManager() != null) {
            if (!deleteNotes) {
            /*    for (Note note : getDataManager().getNotesFroTag(tag.getNameTag())) {
                    note.setTag("");
                    getDataManager().updateNote(note);
                }

             */
            } else {
             /*   for (Note note : getDataManager().getNotesFroTag(tag.getNameTag())) {
                    getDataManager().moveToTrash(note);
                    getDataManager().deleteNote(note);
                }

              */

            }


            getCompositeDisposable().add(getDataManager().deleteTag(tag)
                    .subscribeOn(getSchedulerProvider().io())
                    .subscribe());
        }
    }
}
