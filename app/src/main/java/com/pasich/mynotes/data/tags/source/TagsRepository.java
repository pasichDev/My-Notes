package com.pasich.mynotes.data.tags.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class TagsRepository {

  private static TagsRepository instance;
  private Executor executor;
  private TagsDao tagsDao;

  private TagsRepository(Executor executor, TagsDao tagsDao) {
    this.executor = executor;
    this.tagsDao = tagsDao;
  }

  public static TagsRepository getInstance(TagsDao tagsDao) {
    if (instance == null) {
      instance = new TagsRepository(new DiskExecutor(), tagsDao);
    }
    return instance;
  }

  private MutableLiveData<List<Tag>> getDefaultTags() {
    ArrayList<Tag> defaultList = new ArrayList<Tag>();
    defaultList.add(new Tag().create("", 1, false));

    defaultList.add(new Tag().create("All notes", 2, true));
    MutableLiveData<List<Tag>> liveData = new MutableLiveData<List<Tag>>();
    liveData.setValue(defaultList);
    return liveData;
  }

  public LiveData<List<Tag>> getTags() {
    LiveData<List<Tag>> mTags;
    mTags = tagsDao.getTags();

    return mTags;
  }

  public void insert(Tag tag) {

    Runnable runnable = () -> tagsDao.addTag(tag);
    executor.execute(runnable);
  }

  public void deleteTag(Tag tag) {

    Runnable runnable = () -> tagsDao.deleteTag(tag);
    executor.execute(runnable);
  }

  public void destroyInstance() {
    instance = null;
    tagsDao = null;
    executor = null;
  }
}
