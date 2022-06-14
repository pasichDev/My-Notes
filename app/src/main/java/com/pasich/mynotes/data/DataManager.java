package com.pasich.mynotes.data;

import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;
import com.preference.PowerPreference;
import com.preference.Preference;

public class DataManager {

  private static DataManager sInstance;

  public DataManager() {
    // This class is not publicly instantiable
  }

  public static synchronized DataManager getInstance() {
    if (sInstance == null) {
      sInstance = new DataManager();
    }
    return sInstance;
  }

  public Preference getDefaultPreference() {
    return PowerPreference.getDefaultFile();
  }

  public TagsRepository getTagsRepository() {

    TagsDao tagsDao = DatabaseApp.getInstance().getTagsDao();

    return TagsRepository.getInstance();
  }
}
