package com.pasich.mynotes.ui.helloUI.tool;

import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.TrashNote;

public interface SavesNotes {
    void saveNote(Note note);

    void saveTrash(TrashNote note);
}
