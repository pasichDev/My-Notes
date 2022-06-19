package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.di.App.getApp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.di.main.MainActivityModule;
import com.pasich.mynotes.otherClasses.controllers.activity.NoteActivity;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.view.dialogs.ChoiceNoteDialog.ChoiceNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.ChoiceTagDialog.ChoiceTagDialog;
import com.pasich.mynotes.ui.view.dialogs.MoreActivityDialog;
import com.pasich.mynotes.ui.view.dialogs.NewTagDialog;
import com.pasich.mynotes.utils.MainUtils;
import com.pasich.mynotes.utils.adapters.NotesAdapter;
import com.pasich.mynotes.utils.adapters.TagsAdapter;
import com.pasich.mynotes.utils.other.FormatListUtils;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.view {

  @Inject public MainContract.presenter mainPresenter;
  @Inject public MainUtils utils;
  @Inject public FormatListUtils formatList;
  @Inject public DataManager dataManager;

  private ActivityMainBinding binding;
  private TagsAdapter tagsAdapter;
  private NotesAdapter notesAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
    init();

    mainPresenter.attachView(this);
    mainPresenter.setDataManager(dataManager);
    mainPresenter.viewIsReady();
  }

  @Override
  public void init() {
    getApp()
        .getComponentsHolder()
        .getActivityComponent(getClass(), new MainActivityModule())
        .inject(MainActivity.this);
    binding.setPresenter((MainPresenter) mainPresenter);
  }

  @Override
  public void initListeners() {

    tagsAdapter.setOnItemClickListener(
        new TagsAdapter.OnItemClickListener() {
          @Override
          public void onClick(int position) {
            mainPresenter.clickTag(tagsAdapter.getCurrentList().get(position), position);
          }

          @Override
          public void onLongClick(int position) {
            mainPresenter.clickLongTag(tagsAdapter.getCurrentList().get(position));
          }
        });

    notesAdapter.setOnItemClickListener(
        new NotesAdapter.OnItemClickListener() {

          @Override
          public void onClick(int position) {}

          @Override
          public void onLongClick(int position) {
            mainPresenter.clickLongNote(notesAdapter.getCurrentList().get(position));
          }
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mainPresenter.detachView();
    if (isFinishing()) {
      mainPresenter.destroy();
      getApp().getComponentsHolder().releaseActivityComponent(getClass());
    }
  }

  @Override
  public void settingsSearchView() {
    binding.actionSearch.setSubmitButtonEnabled(false);
    LinearLayout llSearchView = (LinearLayout) binding.actionSearch.getChildAt(0);
    llSearchView.addView(utils.addButtonSearchView(this, R.drawable.ic_sort, R.id.sortButton));
    llSearchView.addView(
        utils.addButtonSearchView(this, R.drawable.ic_edit_format_list, R.id.formatButton));
    formatList.init(findViewById(R.id.formatButton));
  }

  @Override
  public void settingsTagsList(LiveData<List<Tag>> tagList) {
    binding.listTags.addItemDecoration(new SpacesItemDecoration(5));
    binding.listTags.setLayoutManager(
        new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    tagsAdapter = new TagsAdapter(new TagsAdapter.tagDiff());
    binding.listTags.setAdapter(tagsAdapter);
    tagList.observe(
        this,
        tags -> {
          tagsAdapter.submitList(tags);
        });
  }

  @Override
  public void settingsNotesList(int countColumn, LiveData<List<Note>> noteList) {
    binding.listNotes.addItemDecoration(new SpacesItemDecoration(15));
    binding.listNotes.setLayoutManager(
        new StaggeredGridLayoutManager(countColumn, LinearLayoutManager.VERTICAL));
    notesAdapter = new NotesAdapter(new NotesAdapter.noteDiff());
    binding.listNotes.setAdapter(notesAdapter);
    noteList.observe(
        this,
        notes -> {
          notesAdapter.submitList(notes);
          /* binding.setEmptyNotes(notesAdapter.getCurrentList().size() == 0);*/
        });
  }

  @Override
  public void deleteNote(Note note) {
    mainPresenter.deleteNote(note);
  }

  @Override
  public void selectTagUser(int position) {
    tagsAdapter.chooseTag(position);
  }

  @Override
  public void addTag(String nameTag) {
    mainPresenter.addTag(nameTag);
  }

  @Override
  public void deleteTag(Tag tag) {
    mainPresenter.deleteTag(tag);
  }

  @Override
  public void editVisibility(Tag tag) {
    mainPresenter.editVisibility(tag);
  }

  @Override
  public void newNotesButton() {
    startActivity(new Intent(this, NoteActivity.class));
  }

  @Override
  public void moreActivity() {
    new MoreActivityDialog().show(getSupportFragmentManager(), "more activity");
  }

  @Override
  public void startCreateTagDialog() {
    new NewTagDialog().show(getSupportFragmentManager(), "New Tag");
  }

  @Override
  public void choiceTagDialog(Tag tag, Integer[] arg) {
    new ChoiceTagDialog(tag, arg).show(getSupportFragmentManager(), "ChoiceDialog");
  }

  @Override
  public void choiceNoteDialog(Note note) {
    /* String[] keysNote = {
            String.valueOf(position),
            String.valueOf(ListNotesAdapter.getItem(position).getId()),
            ListNotesAdapter.getItem(position).getDate(),
            String.valueOf(ListNotesAdapter.getItem(position).getValue().length())
    };*/

    String[] keysNote = {};
    new ChoiceNoteDialog(note).show(getSupportFragmentManager(), "ChoiceDialog");
  }

  @Override
  public void onBackPressed() {
    utils.CloseApp(MainActivity.this);
  }
}
