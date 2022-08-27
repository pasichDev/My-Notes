package com.pasich.mynotes.base.view;


import com.pasich.mynotes.data.notes.Note;

public interface NoteView {

  void deleteNote(Note note);

  void actionStartNote(int position);

  void tagNoteSelected(Note note);

}
