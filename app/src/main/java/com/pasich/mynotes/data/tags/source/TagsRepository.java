package com.pasich.mynotes.data.tags.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.List;
import java.util.concurrent.Executor;

public class TagsRepository {

  private static TagsRepository instance;
  private final Executor executor;
  private final TagsDao tagsDao;
  private LiveData<List<Tag>> mTags = new MutableLiveData<>();

  private TagsRepository(Executor executor, TagsDao tagsDao) {
    this.executor = executor;
    this.tagsDao = tagsDao;
    loadingTags();
  }

  public static TagsRepository getInstance(TagsDao tagsDao) {
    if (instance == null) {
      instance = new TagsRepository(new DiskExecutor(), tagsDao);
    }

    return instance;
  }

  private void loadingTags() {
    Runnable runnable =
        () -> {
          mTags = tagsDao.getTags();
        };
    executor.execute(runnable);
  }

  public LiveData<List<Tag>> getTags() {

    return mTags;
  }

  public void insert(Tag tag) {

    Runnable runnable =
        () -> {
          tagsDao.addTag(tag);
        };
    executor.execute(runnable);
  }

  public void destroyInstance() {
    instance = null;
  }
}
