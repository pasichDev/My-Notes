package com.pasich.mynotes.data.tags.source;


public class TagsRepository implements TagsDataSource {

  private static TagsRepository instance;

  private final TagsDataSource dataSource;

  private TagsRepository(TagsDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public static TagsRepository getInstance(TagsDataSource dataSource) {
    if (instance == null) {
      instance = new TagsRepository(dataSource);
    }
    return instance;
  }

  public void destroyInstance() {
    instance = null;
  }

  @Override
  public void getTags(LoadTagsCallback callback) {
    if (callback == null) return;
    dataSource.getTags(callback);
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

}
