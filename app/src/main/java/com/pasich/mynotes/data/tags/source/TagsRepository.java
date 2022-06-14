package com.pasich.mynotes.data.tags.source;

public class TagsRepository {

  private static TagsRepository instance;

  private TagsRepository() {}

  public static TagsRepository getInstance() {
    if (instance == null) {
      instance = new TagsRepository();
    }
    return instance;
  }

  public void destroyInstance() {
    instance = null;
  }
}
