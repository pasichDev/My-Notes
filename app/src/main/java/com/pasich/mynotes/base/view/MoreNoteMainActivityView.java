package com.pasich.mynotes.base.view;


import com.pasich.mynotes.data.model.Note;

public interface MoreNoteMainActivityView {

    void actionStartNote(Note note, int position);

    void openCopyNote(int idNote);

    void callbackDeleteNote(Note mNote);

}
