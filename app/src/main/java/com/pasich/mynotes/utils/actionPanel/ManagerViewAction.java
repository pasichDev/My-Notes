package com.pasich.mynotes.utils.actionPanel;

import com.pasich.mynotes.data.notes.Note;

import java.util.ArrayList;

public interface ManagerViewAction {
    void activateActionPanel();

    void deactivationActionPanel();

    void deleteNotes(ArrayList<Note> notes);

    void shareNotes(ArrayList<Note> notes);
}
