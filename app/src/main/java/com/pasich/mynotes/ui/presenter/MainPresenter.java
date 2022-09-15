package com.pasich.mynotes.ui.presenter;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.data.trash.source.TrashRepository;
import com.pasich.mynotes.ui.contract.MainContract;

import java.util.ArrayList;
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
        this.data = dataManager;
        this.tagsRepository = data.getTagsRepository();
        this.notesRepository = data.getNotesRepository();
        this.trashRepository = data.getTrashRepository();
    }

    @Override
    public void viewIsReady() {
        getView().settingsSearchView();
        getView().settingsTagsList(tagsRepository.getTags());
        getView().settingsNotesList(notesRepository.getNotes());
        getView().initListeners();
        getView().initActionUtils();
    }


    @Override
    public void destroy() {
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
                for (Note note : notesRepository.getNotesFromTag(tag.getNameTag())) {
                    note.setTag("");
                    notesRepository.updateNote(note);
                }
            } else {
                for (Note note : notesRepository.getNotesFromTag(tag.getNameTag())) {
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
            getView().selectTagUser(position,
                    tag.getSystemAction() == 2 ? notesRepository.getNotes() : notesRepository.getNotesFromTagLiveData(tag.getNameTag()));
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
    public void deleteNote(Note note) {
        if (data != null) {
            trashRepository.moveToTrash(note);
            notesRepository.deleteNote(note);
        }
    }

    @Override
    public void deleteNotesArray(ArrayList<Note> notes) {
        if (data != null) {
            trashRepository.moveToTrash(notes);
            notesRepository.deleteNote(notes);
        }
    }


}
