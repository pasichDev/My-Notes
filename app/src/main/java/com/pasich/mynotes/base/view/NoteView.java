package com.pasich.mynotes.base.view;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

public interface NoteView {

  void deleteNote(Note note);

  void actionStartNote();

  void editTagForNote(Tag tag, Note note);

  void tagNoteSelected(Note note);

}
