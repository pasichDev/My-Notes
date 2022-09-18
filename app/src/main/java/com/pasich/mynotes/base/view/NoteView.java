package com.pasich.mynotes.base.view;


import com.pasich.mynotes.data.notes.Note;

public interface NoteView {

  void deleteNote(Note note);

  void actionStartNote(Note note, int position);

  void tagNoteSelected(Note note);

}
