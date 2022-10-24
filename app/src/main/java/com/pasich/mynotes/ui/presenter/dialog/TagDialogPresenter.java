package com.pasich.mynotes.ui.presenter.dialog;


import com.pasich.mynotes.base.dialog.BasePresenterDialog;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.ui.contract.dialog.TagDialogContract;

import java.util.concurrent.ExecutionException;


public class TagDialogPresenter extends BasePresenterDialog<TagDialogContract.view>
        implements TagDialogContract.presenter {

    private TagsRepository tagsRepository;
    private NotesRepository notesRepository;

    public TagDialogPresenter() {
    }

    @Override
    public void setDataManager(DataManager dataManager) {
        tagsRepository = dataManager.getTagsRepository();
        notesRepository = dataManager.getNotesRepository();
    }

    @Override
    public void viewIsReady() {
        try {
            getView().loadingTagsOfChips(tagsRepository.getTagsUser());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


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
        notesRepository.setTagNote(nameTag, noteId);
    }

    @Override
    public int getCountTags() throws ExecutionException, InterruptedException {
        return tagsRepository.getCountTagAll();
    }

    @Override
    public void createTagNote(Tag tag, int noteId) {
        tagsRepository.addTag(tag);
        notesRepository.setTagNote(tag.getNameTag(), noteId);
    }

    @Override
    public void removeTagNote(int noteId) {
        notesRepository.setTagNote("", noteId);
    }
}
