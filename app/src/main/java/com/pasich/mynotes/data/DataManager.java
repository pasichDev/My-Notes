package com.pasich.mynotes.data;

import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.preference.PowerPreference;
import com.preference.Preference;

public class DataManager {

  private static DataManager sInstance;

  public DataManager() {
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
    return TagsRepository.getInstance(DatabaseApp.getInstance().getTagsDao());
  }
}
