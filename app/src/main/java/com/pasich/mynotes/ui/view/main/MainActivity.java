package com.pasich.mynotes.ui.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.app.App;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.otherClasses.controllers.activity.NoteActivity;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.view.main.dagger.MainActivityModule;
import com.pasich.mynotes.utils.MainUtils;
import com.pasich.mynotes.utils.adapters.TagAdapter;
import com.pasich.mynotes.utils.other.FormatListUtils;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.view {

  @Inject public MainContract.presenter mainPresenter;
  @Inject public MainUtils utils;
  @Inject public FormatListUtils formatList;

  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
    init();

    mainPresenter.attachView(this);
    mainPresenter.viewIsReady();
  }

  @Override
  public void init() {
    // inject activity
    App.getApp(this)
        .getComponentsHolder()
        .getActivityComponent(getClass(), new MainActivityModule())
        .inject(this);
    binding.setPresenter((MainPresenter) mainPresenter);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mainPresenter.detachView();
    if (isFinishing()) {
      mainPresenter.destroy();
      App.getApp(this).getComponentsHolder().releaseActivityComponent(getClass());
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
  public void settingsTagsList() {
    binding.listTags.addItemDecoration(new SpacesItemDecoration(5));
    binding.listTags.setLayoutManager(
        new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    ArrayList<Tag> tags = new ArrayList<>();

    tags.add(new Tag("", 1, false));
    tags.add(new Tag(getString(R.string.allNotes), 2, true));
    tags.add(new Tag("test", 0, false));

    TagAdapter adapter = new TagAdapter(tags);
    binding.listTags.setAdapter(adapter);
  }

  @Override
  public void setEmptyListNotes() {
    // Здесь нужно реализовать проверку на количество елементов в адаптере заметок
    binding.setEmptyNotes(true);
  }

  /** Method that changes the number of GridView columns */
  @Override
  public void settingsNotesList(int countColumn) {
    binding.listNotes.addItemDecoration(new SpacesItemDecoration(15));
    binding.listNotes.setLayoutManager(
        new StaggeredGridLayoutManager(countColumn, LinearLayoutManager.VERTICAL));
  }

  @Override
  public void newNotesButton() {
    startActivity(new Intent(this, NoteActivity.class));
  }

  @Override
  public void onBackPressed() {
    utils.CloseApp(MainActivity.this);
  }
}
