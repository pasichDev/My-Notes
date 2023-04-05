package com.pasich.mynotes.ui.contract;


import android.content.Intent;

import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.base.view.BasePresenter;
import com.pasich.mynotes.base.view.BaseView;
import com.pasich.mynotes.base.view.MoreNoteNoteActivityView;
import com.pasich.mynotes.base.view.ShortCutView;
import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.data.model.Note;
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

        String getShareText();

        void setShareText(String shareText);

        long getIdKey();

        void setIdKey(long idKey);

        Note getNote();

        void setNote(Note mNote);

        String getTagNote();

        void setTagNote(String tagNote);

        boolean getExitNoteSave();

        void setExitNoSave(boolean exitNoSave);

        boolean getNewNotesKey();

        void setNewNoteKey(boolean newNoteKey);

        int getTypeFace(String textStyle);

        List<ItemListNote> getListNotesItems();

        int getStatusList();

        void setStatusList(int statusList);

        void deleteList(int idNote);
    }
}
