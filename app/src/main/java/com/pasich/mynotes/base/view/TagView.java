package com.pasich.mynotes.base.view;

import com.pasich.mynotes.data.database.model.Tag;

public interface TagView {
  void deleteTag(Tag tag, boolean deleteNotes);

  void editVisibility(Tag tag);

}
