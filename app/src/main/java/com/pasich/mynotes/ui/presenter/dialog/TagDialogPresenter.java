package com.pasich.mynotes.ui.presenter.dialog;


import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.ui.contract.dialog.TagDialogContract;


public class TagDialogPresenter extends PresenterBase<TagDialogContract.view>
        implements TagDialogContract.presenter {

    private DataManager data;
    private TagsRepository tagsRepository;
    private NotesRepository notesRepository;

    public TagDialogPresenter() {
    }

    @Override
    public void setDataManager(DataManager dataManager) {
        data = dataManager;
        tagsRepository = data.getTagsRepository();
        notesRepository = data.getNotesRepository();
    }

    @Override
    public void viewIsReady() {
        getView().settingsTagsList(1, tagsRepository.getTagsUser());
        getView().initListeners();
    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {
        tagsRepository.destroyInstance();
        notesRepository.destroyInstance();
        data = null;
    }


    @Override
    public void editTagNote(Tag tag, Note note) {
        note.setTag(tag.getNameTag());
        notesRepository.updateNote(note);
    }

    @Override
    public void createTagNote(Tag tag, Note note) {
        tagsRepository.insert(tag);
        note.setTag(tag.getNameTag());
        notesRepository.updateNote(note);
    }

    @Override
    public void removeTagNote(Note note) {
        note.setTag("");
        notesRepository.updateNote(note);
    }
}
