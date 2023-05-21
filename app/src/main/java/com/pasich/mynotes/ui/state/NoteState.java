package com.pasich.mynotes.ui.state;

import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.utils.constants.LIST_STATUS;

import java.util.ArrayList;
import java.util.List;

public class NoteState {

    private String shareText, tagNote;
    private long idKey;
    private Note mNote;
    private boolean exitNoSave = false, newNoteKey;

    private int statusList = LIST_STATUS.NOT;

    private List<ItemListNote> listNotesItems = new ArrayList<>();

    private void NoteState() {

    }

    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText == null ? "" : shareText;
    }

    public long getIdKey() {
        return idKey;
    }

    public void setIdKey(long idKey) {
        this.idKey = idKey;
    }

    public Note getNote() {
        return mNote;
    }

    public void setNote(Note mNote) {
        this.mNote = mNote;
    }

    public String getTagNote() {
        return tagNote;
    }

    public void setTagNote(String tagNote) {
        this.tagNote = tagNote == null ? "" : tagNote;
    }

    public boolean getNewNotesKey() {
        return newNoteKey;
    }

    public void setNewNoteKey(boolean newNoteKey) {
        this.newNoteKey = newNoteKey;
    }

    public boolean getExitNoteSave() {
        return exitNoSave;
    }

    public void setExitNoSave(boolean exitNoSave) {
        this.exitNoSave = exitNoSave;
    }

    public int getStatusList() {
        return statusList;
    }

    public void setStatusList(int statusList) {
        this.statusList = statusList;
    }


    public void setListNotesItems(List<ItemListNote> listNotesItems) {
        this.listNotesItems = listNotesItems;
    }

    public List<ItemListNote> getListNotesItems() {
        return listNotesItems;
    }
}
