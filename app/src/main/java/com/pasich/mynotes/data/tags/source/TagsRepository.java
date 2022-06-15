package com.pasich.mynotes.data.tags.source;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;

import java.util.List;

public class TagsRepository implements TagsDataSource {

  private static TagsRepository instance;

  private TagsDao tagsDao;

  private TagsRepository(TagsDao tagsDao) {
    this.tagsDao = tagsDao;
  }

  public static TagsRepository getInstance(TagsDao tagsDao) {
    if (instance == null) {
      instance = new TagsRepository(tagsDao);
    }
    return instance;
  }

  public void destroyInstance() {
    instance = null;
  }
  /*
  public void addTag(String nameTag, int visibility) {
    Tag tag = new Tag();
    tag.setNameTag(nameTag);
    tag.setVisibility(visibility);

    tagsDao.addTag(tag);
  }

  public List<Tag> getsTags() {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        List<Tag> tags = tagsDao.getTags();

    };
      }
    executor.execute(runnable);

  }*/

  @Override
  public void getTags(LoadTagsCallback callback) {}

  @Override
  public void saveTags(List<Tag> movies) {}
}
