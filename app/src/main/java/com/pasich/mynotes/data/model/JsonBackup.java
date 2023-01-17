package com.pasich.mynotes.data.model;

import java.util.ArrayList;
import java.util.List;

public class JsonBackup {

    private ArrayList<Note> notes;
    private ArrayList<TrashNote> trashNotes;

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void setTrashNotes(ArrayList<TrashNote> trashNotes) {
        this.trashNotes = trashNotes;
    }
}
