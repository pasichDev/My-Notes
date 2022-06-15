package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.di.App.getApp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.di.main.MainActivityModule;
import com.pasich.mynotes.otherClasses.controllers.activity.NoteActivity;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.view.dialogs.MoreActivityDialog;
import com.pasich.mynotes.utils.MainUtils;
import com.pasich.mynotes.utils.adapters.TagAdapter;
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
  private TagAdapter tagsAdapter;

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
    getApp(this)
        .getComponentsHolder()
        .getActivityComponent(getClass(), new MainActivityModule())
        .inject(this);
    binding.setPresenter((MainPresenter) mainPresenter);
  }

  @Override
  public void initListeners() {
    tagsAdapter.setOnItemClickListener(
        new TagAdapter.OnItemClickListener() {

          @Override
          public void onClick(int position) {}

          @Override
          public void onLongClick(int position) {}
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mainPresenter.detachView();
    if (isFinishing()) {
      mainPresenter.destroy();
      getApp(this).getComponentsHolder().releaseActivityComponent(getClass());
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
  public void settingsTagsList(List<Tag> tagList) {
    binding.listTags.addItemDecoration(new SpacesItemDecoration(5));
    binding.listTags.setLayoutManager(
        new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    tagsAdapter = new TagAdapter(tagList);
    binding.listTags.setAdapter(tagsAdapter);
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
  public void moreActivity() {
    new MoreActivityDialog().show(getSupportFragmentManager(), "more activity");
  }

  @Override
  public DataManager getDataManager() {
    return dataManager;
  }

  @Override
  public void onBackPressed() {
    utils.CloseApp(MainActivity.this);
  }
}
