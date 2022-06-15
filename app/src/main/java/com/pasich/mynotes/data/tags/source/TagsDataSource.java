package com.pasich.mynotes.data.tags.source;

import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

public interface TagsDataSource {

  interface LoadTagsCallback {
    void onTagsLoaded(List<Tag> tags);
  }

  interface OptionTagCallback {
    void addTag(Tag tag);

    void deleteTag(Tag tag);
  }

  void getTags(LoadTagsCallback callback);

}
