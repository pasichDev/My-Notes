package com.pasich.mynotes.data.tags.source;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.List;
import java.util.concurrent.Executor;

public class DataSourceTags implements TagsDataSource {

  private final Executor executor;
  private final TagsDao tagsDao;

  private static DataSourceTags instance;

  private DataSourceTags(Executor executor, TagsDao tagsDao) {
    this.executor = executor;
    this.tagsDao = tagsDao;
  }

  public static DataSourceTags getInstance(TagsDao tagsDao) {
    if (instance == null) {
      instance = new DataSourceTags(new DiskExecutor(), tagsDao);
    }
    return instance;
  }

  @Override
  public void getTags(LoadTagsCallback callback) {
    Runnable runnable =
        () -> {
          List<Tag> tags = tagsDao.getTags();
          if (!tags.isEmpty()) {
            callback.onTagsLoaded(tags);
          }
        };
    executor.execute(runnable);
  }
}
