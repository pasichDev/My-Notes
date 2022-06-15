package com.pasich.mynotes.data.tags.source;

import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

public interface TagsDataSource {

  interface LoadTagsCallback {
    void onTagsLoaded(List<Tag> tags);

    void onDataNotAvailable();

    void onError();
  }

  void getTags(LoadTagsCallback callback);

  void saveTags(List<Tag> movies);
}
