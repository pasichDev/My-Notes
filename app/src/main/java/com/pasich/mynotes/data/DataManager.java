package com.pasich.mynotes.data;

import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.preference.PowerPreference;
import com.preference.Preference;

public class DataManager {


  public Preference getDefaultPreference() {
    return PowerPreference.getDefaultFile();
  }

  public TagsRepository getTagsRepository() {
    return TagsRepository.getInstance(DatabaseApp.getInstance().tagsDao());
  }

  public NotesRepository getNotesRepository() {
    return NotesRepository.getInstance(DatabaseApp.getInstance().noteDao());
  }
}
