package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.ui.contract.MainContract;

public class MainPresenter extends PresenterBase<MainContract.view>
    implements MainContract.presenter {

  private DataManager data;
  private TagsRepository tagsRepository;
  private NotesRepository notesRepository;

  public MainPresenter() {}

  @Override
  public void setDataManager(DataManager dataManager) {
    data = dataManager;
    tagsRepository = data.getTagsRepository();
    notesRepository = data.getNotesRepository();
  }

  @Override
  public void viewIsReady() {
    getView().settingsSearchView();
    getView().settingsTagsList(tagsRepository.getTags());
    getView().initListeners();
    getView().settingsNotesList(getFormatParam(), notesRepository.getNotes());
  }

  private int getFormatParam() {
    return data.getDefaultPreference().getInt("formatParam", 1);
  }

  @Override
  public void detachView() {}

  @Override
  public void destroy() {
    tagsRepository.destroyInstance();
    notesRepository.destroyInstance();
    data = null;
  }

  @Override
  public void newNotesClick() {
    if (isViewAttached()) getView().newNotesButton();
  }

  @Override
  public void moreActivityClick() {
    if (isViewAttached()) getView().moreActivity();
  }

  @Override
  public void addTag(String nameTag) {
    if (data != null) tagsRepository.insert(new Tag().create(nameTag));
  }

  @Override
  public void deleteTag(Tag tag) {
    if (data != null) tagsRepository.deleteTag(tag);
  }

  @Override
  public void editVisibility(Tag tag) {
    if (data != null) tagsRepository.updateTag(tag);
  }

  @Override
  public void clickTag(Tag tag, int position) {
    if (tag.getSystemAction() == 1) {
      getView().startCreateTagDialog();
    } else {
      getView().selectTagUser(position);
    }
  }

  @Override
  public void clickLongTag(Tag tag) {
    if (tag.getSystemAction() == 0) {
      Integer[] keysNote = {notesRepository.getCountNoteTag(tag.getNameTag())};
      getView().choiceTagDialog(tag, keysNote);
    }
  }
}
