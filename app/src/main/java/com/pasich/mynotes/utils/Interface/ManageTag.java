package com.pasich.mynotes.utils.Interface;

public interface ManageTag {
  void addTag(String tagName, int noteId, int position);

  void addTagForNote(String tagName, int noteId, int position);

  void deleteTag(boolean deleteNotes, int position);

}
