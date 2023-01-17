package com.pasich.mynotes.data.model;

import java.util.List;

public class JsonBackup {

    private List<Note> notes;
    private List<TrashNote> trashNotes;

    public JsonBackup() {
    }

    public boolean setNotes(List<Note> notes) {
        this.notes = notes;
        return notes.size() >= 1;
    }

    public void setTrashNotes(List<TrashNote> trashNotes) {
        this.trashNotes = trashNotes;
    }


}
