package com.pasich.mynotes.ui.contract;


import android.content.Intent;

import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.base.view.BasePresenter;
import com.pasich.mynotes.base.view.BaseView;
import com.pasich.mynotes.base.view.MoreNoteNoteActivityView;
import com.pasich.mynotes.base.view.ShortCutView;
import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.ui.state.NoteState;
import com.pasich.mynotes.utils.bottomPanelNote.BottomPanelNoteCallback;

import java.util.List;

public interface NoteContract {

    interface view extends BaseView, ActionBar, MoreNoteNoteActivityView, ShortCutView, BottomPanelNoteCallback {

        void initTypeActivity();

        void closeNoteActivity();

        void activatedActivity();

        void loadingNote(Note note);

        void loadingListNote(List<ItemListNote> listItemsNote);

        void editIdNoteCreated(long idNote);
    }

    interface presenter extends BasePresenter<view> {
        void closeActivity();

        void getLoadIntentData(Intent mIntent);

        void loadingData(long idNote);

        void activateEditNote();

        void createNote(Note note);

        void saveNote(Note note);

        void saveItemList(List<ItemListNote> updateList, List<ItemListNote> deleteList);

        void deleteNote(Note note);

        int getTypeFace(String textStyle);

        NoteState getNoteState();

        void deleteList(int idNote);
    }
}
