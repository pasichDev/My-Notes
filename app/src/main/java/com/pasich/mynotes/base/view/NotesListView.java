package com.pasich.mynotes.base.view;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.notes.Note;

import java.util.List;

public interface NotesListView {
  void settingsNotesList(int countColumn, LiveData<List<Note>> noteList);
}
