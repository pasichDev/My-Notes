package com.pasich.mynotes.base.view;

import com.pasich.mynotes.data.database.notes.Note;

public interface RestoreNotesBackupOld {
    void errorProcessRestore();

    void successfullyProcessRestore(int countNotes);

    void saveNoteRestore(Note newNote);
}
