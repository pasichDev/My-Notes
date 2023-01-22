package com.pasich.mynotes.data.model.backup;

import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.data.model.Tag;
import com.pasich.mynotes.data.model.TrashNote;

import java.util.ArrayList;
import java.util.List;

public class JsonBackup {

    public PreferencesBackup preferences;
    public List<Note> notes;
    public List<TrashNote> trashNotes;

    public List<Tag> tags;

    public JsonBackup() {
        this.preferences = new PreferencesBackup();
        this.notes = getNotes();
        this.tags = getTags();
        this.trashNotes = getTrashNotes();
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

    public void setPreferences(PreferencesBackup preferences) {
        this.preferences = preferences;
    }

    public PreferencesBackup getPreferences() {
        return preferences == null ? new PreferencesBackup() : preferences;
    }
}
