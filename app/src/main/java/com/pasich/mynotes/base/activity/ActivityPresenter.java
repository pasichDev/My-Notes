package com.pasich.mynotes.base.activity;

import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.DataManagerNew;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.data.trash.source.TrashRepository;

public interface ActivityPresenter<V extends BaseViewActivity> {

  void attachView(V mVIew);

  void setDataManager(DataManager dataManager);

  void viewIsReady();

  void detachView();

  void destroy();

  DataManagerNew getDataManager();

  TrashRepository getTrashRepository();

  NotesRepository getNotesRepository();

  TagsRepository getTagsRepository();
}
