package com.pasich.mynotes.ui.presenter;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import android.util.Log;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.data.trash.source.TrashRepository;
import com.pasich.mynotes.ui.contract.MainContract;

import java.util.concurrent.ExecutionException;

public class MainPresenter extends PresenterBase<MainContract.view>
        implements MainContract.presenter {

    private DataManager data;
    private TagsRepository tagsRepository;
    private NotesRepository notesRepository;
    private TrashRepository trashRepository;

    public MainPresenter() {
    }

    @Override
    public void setDataManager(DataManager dataManager) {
        data = dataManager;
        tagsRepository = data.getTagsRepository();
        notesRepository = data.getNotesRepository();
        trashRepository = data.getTrashRepository();
    }

    @Override
    public void viewIsReady() {
        getView().settingsSearchView();
        getView().settingsTagsList(tagsRepository.getTags());
        getView().settingsNotesList(notesRepository.getNotes());
        getView().initListeners();
    }

    @Override
    public void detachView() {
    }

    @Override
    public void destroy() {
        tagsRepository.destroyInstance();
        trashRepository.destroyInstance();
        notesRepository.destroyInstance();
        data = null;
    }

    @Override
    public void newNotesClick() {
        if (isViewAttached()) getView().newNotesButton();
    }

    @Override
    public void moreActivityClick() {
        if (isViewAttached()) getView().moreActivity();
    }

    @Override
    public void deleteTag(Tag tag, boolean deleteNotes) throws ExecutionException, InterruptedException {
        if (data != null) {
            if (!deleteNotes) {
                notesRepository.clearTagForNotes(tag.getNameTag());
            }else {
                for (Note note : notesRepository.getNotesFromTag(tag.getNameTag())){
                    trashRepository.moveToTrash(note);
                    notesRepository.deleteNote(note);
                }

            }
            tagsRepository.deleteTag(tag);
        }
    }



    @Override
    public void editVisibility(Tag tag) {
        if (data != null) tagsRepository.updateTag(tag);
    }

    @Override
    public void clickTag(Tag tag, int position) {
        if (tag.getSystemAction() == 1) {
            try {
                if (tagsRepository.getCountTagAll() >= MAX_TAG_COUNT) {
                    getView().startToastCheckCountTags();
                } else
                    getView().startCreateTagDialog();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            getView().selectTagUser(position);
        }
    }

    @Override
    public void clickLongTag(Tag tag) {
        if (tag.getSystemAction() == 0) {
            Integer[] keysNote = new Integer[0];
            try {
                keysNote = new Integer[]{notesRepository.getCountNoteTag(tag.getNameTag())};
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            getView().choiceTagDialog(tag, keysNote);
        }
    }

    @Override
    public void clickNote(int idNote) {
        getView().openNoteEdit(idNote);
    }

    @Override
    public void clickLongNote(Note note) {
        getView().choiceNoteDialog(note);
    }

    @Override
    public void deleteNote(Note note) {
        //   trashRepository.moveToTrash(note);
        // notesRepository.deleteNote(note);

        if (tagsRepository == null) Log.wtf("pasic", "tagReposit: null ");
        if (notesRepository == null) Log.wtf("pasic", "noterepo: null ");
    }


}
