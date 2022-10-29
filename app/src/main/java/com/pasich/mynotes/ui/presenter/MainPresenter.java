package com.pasich.mynotes.ui.presenter;

import android.util.Log;

import com.pasich.mynotes.base.activity.BasePresenterActivity;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.ui.contract.MainContract;

import java.util.ArrayList;

import javax.inject.Inject;


public class MainPresenter extends BasePresenterActivity<MainContract.view> implements MainContract.presenter {


    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void viewIsReady() {
        getView().settingsSearchView();
        getView().settingsTagsList();
        getView().settingsNotesList();

        getView().loadingData(getDataManager().getTags(), null);

        getView().initListeners();
    }


    @Override
    public void destroy() {
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


            Log.wtf("pasic", "deleteTag: ");
            getDataManager().deleteTag(tag);
        }
    }


    @Override
    public void editVisibility(Tag tag) {
        //  if (getDataManager() != null) getTagsRepository().updateTag(tag);
    }

    @Override
    public void clickTag(Tag tag, int position) {
     /*   try {
            if (tag.getSystemAction() == 1) {
                if (getTagsRepository().getCountTagAll() >= MAX_TAG_COUNT) {
                    getView().startToastCheckCountTags();
                } else getView().startCreateTagDialog();
            } else {
                getView().selectTagUser(position);
               // notesRepository.setNotesAll(tag.getSystemAction() == 2 ? notesRepository.getNotes() : notesRepository.getNotesFromTag(tag.getNameTag()));


                //position,
                //                        tag.getSystemAction() == 2 ?
                //                                (List<Note>) notesRepository.getNotesAll()
                //                                :
                //                                notesRepository.getNotesFromTag(tag.getNameTag())
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void clickLongTag(Tag tag) {
        if (tag.getSystemAction() == 0) {
            Integer[] keysNote = new Integer[0];
            //    try {
//                keysNote = new Integer[]{getNotesRepository().getCountNoteTag(tag.getNameTag())};
            //   } catch (ExecutionException | InterruptedException e) {
            //     e.printStackTrace();
            //      }
            getView().choiceTagDialog(tag, new Integer[]{0});
        }
    }

    @Override
    public void clickNote(int idNote) {
        getView().openNoteEdit(idNote);
    }


    @Override
    public void deleteNote(Note note) {
        if (getDataManager() != null) {
            //    getTrashRepository().moveToTrash(note);
            //      getNotesRepository().deleteNote(note);
        }
    }

    @Override
    public void deleteNotesArray(ArrayList<Note> notes) {
        if (getDataManager() != null) {
            //     getTrashRepository().moveToTrash(notes);
            //       getNotesRepository().deleteNote(notes);
        }
    }

    @Override
    public void addNote(Note note) {
      /*  try {
            getNotesRepository().addNote(note);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

       */
    }

    @Override
    public void sortButton() {
        getView().sortButton();
    }

    @Override
    public void formatButton() {
        getView().formatButton(getDataManager().getFormatCount());
    }

    @Override
    public void startSearchDialog() {
        getView().startSearchDialog();
    }


}
