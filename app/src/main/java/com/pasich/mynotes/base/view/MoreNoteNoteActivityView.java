package com.pasich.mynotes.base.view;

public interface MoreNoteNoteActivityView {

    void changeTextStyle();

    void changeTextSizeOnline(int sizeText);

    void changeTextSizeOffline();

    void closeActivityNotSaved();

    void changeTag(String nameTag);

    void openCopyNote(long idNote);
}
