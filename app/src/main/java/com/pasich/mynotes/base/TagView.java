package com.pasich.mynotes.base;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

public interface TagView {
  void settingsTagsList(LiveData<List<Tag>> tagList);

  void addTag(String nameTag);
}
