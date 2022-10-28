package com.pasich.mynotes.ui.presenter.dialog;


import com.pasich.mynotes.base.dialog.BasePresenterDialog;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.ui.contract.dialog.TagDialogContract;

import java.util.concurrent.ExecutionException;


public class TagDialogPresenter extends BasePresenterDialog<TagDialogContract.view>
        implements TagDialogContract.presenter {


    public TagDialogPresenter() {
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
