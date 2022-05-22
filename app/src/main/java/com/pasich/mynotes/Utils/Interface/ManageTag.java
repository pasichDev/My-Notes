package com.pasich.mynotes.Utils.Interface;

public interface ManageTag {
  void addTag(String tagName, int noteId);

  void addTagForNote(String tagName, int noteId);

  void deleteTag(boolean deleteNotes);
}
