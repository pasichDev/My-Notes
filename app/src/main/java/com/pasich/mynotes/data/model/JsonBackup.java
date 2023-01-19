package com.pasich.mynotes.data.model;

import java.util.ArrayList;
import java.util.List;

public class JsonBackup {

    public List<Note> notes;
    public List<TrashNote> trashNotes;

    public List<Tag> tags;

    public JsonBackup() {
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void setTrashNotes(List<TrashNote> trashNotes) {
        this.trashNotes = trashNotes;
    }


    public List<Note> getNotes() {
        return notes == null ? new ArrayList<>() : notes;
    }

    public List<TrashNote> getTrashNotes() {
        return trashNotes == null ? new ArrayList<>() : trashNotes;
    }

    public List<Tag> getTags() {
        return tags == null ? new ArrayList<>() : tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
