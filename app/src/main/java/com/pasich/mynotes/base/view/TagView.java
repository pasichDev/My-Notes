package com.pasich.mynotes.base.view;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

public interface TagView {
  void settingsTagsList(LiveData<List<Tag>> tagList);



  void deleteTag(Tag tag, boolean deleteNotes);

  void editVisibility(Tag tag);

}
