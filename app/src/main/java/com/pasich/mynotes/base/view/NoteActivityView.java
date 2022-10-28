package com.pasich.mynotes.base.view;

import com.pasich.mynotes.data.database.notes.Note;

public interface NoteActivityView {

  void deleteNote(Note note);

  void closeActivityNotSaved();
}
