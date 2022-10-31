package com.pasich.mynotes.ui.presenter;


import com.pasich.mynotes.base.AppBasePresenter;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.ui.contract.TagDialogContract;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import java.util.concurrent.ExecutionException;

import io.reactivex.disposables.CompositeDisposable;


public class TagDialogPresenter extends AppBasePresenter<TagDialogContract.view>
        implements TagDialogContract.presenter {


    public TagDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void viewIsReady() {
        //    try {
        //         getView().loadingTagsOfChips(tagsRepository.getTagsUser());
        //      } catch (ExecutionException | InterruptedException e) {
        //          e.printStackTrace();
        //     }


        getView().initListeners();
    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public DataManager getDataManager() {
        return null;
    }


    @Override
    public void editTagNote(String nameTag, int noteId) {
        //   notesRepository.setTagNote(nameTag, noteId);
    }

    @Override
    public int getCountTags() throws ExecutionException, InterruptedException {
        //      return tagsRepository.getCountTagAll();
        return 0;
    }

    @Override
    public void createTagNote(Tag tag, int noteId) {
        //   tagsRepository.addTag(tag);
        //   notesRepository.setTagNote(tag.getNameTag(), noteId);
    }

    @Override
    public void removeTagNote(int noteId) {
        //    notesRepository.setTagNote("", noteId);
    }
}
