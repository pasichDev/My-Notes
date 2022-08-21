package com.pasich.mynotes.ui.presenter.dialog;


import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.ui.contract.dialog.TagDialogContract;

import java.util.concurrent.ExecutionException;


public class TagDialogPresenter extends PresenterBase<TagDialogContract.view>
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
      /*  getView().settingsTagsList(1, tagsRepository.getTagsUser());
        try {
            getView().visibilityAddTagButton(tagsRepository.getCountTagAll() < MAX_TAG_COUNT);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

       */
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
    public void editTagNote(String nameTag, Note note) {
        note.setTag(nameTag);
        notesRepository.updateNote(note);
    }

    @Override
    public int getCountTags() throws ExecutionException, InterruptedException {
        return tagsRepository.getCountTagAll();
    }

    @Override
    public void createTagNote(Tag tag, Note note) {
        tagsRepository.addTag(tag);
        note.setTag(tag.getNameTag());
        notesRepository.updateNote(note);
    }

    @Override
    public void removeTagNote(Note note) {
        note.setTag("");
        notesRepository.updateNote(note);
    }
}
