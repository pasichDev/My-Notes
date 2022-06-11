package com.pasich.mynotes.ui.view.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.app.App;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.otherClasses.controllers.activity.NoteActivity;
import com.pasich.mynotes.otherClasses.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.ui.contract.TagsContract;
import com.pasich.mynotes.ui.view.main.dagger.MainActivityModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements TagsContract.view {

  @Inject public TagsContract.presenter tagsPresenter;


  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

    // inject activity
    App.getApp(this)
        .getComponentsHolder()
        .getActivityComponent(getClass(), new MainActivityModule())
        .inject(this);

    tagsPresenter.attachView(this);
    tagsPresenter.viewIsReady();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    tagsPresenter.detachView();
    if (isFinishing()) {
      tagsPresenter.destroy();
      App.getApp(this).getComponentsHolder().releaseActivityComponent(getClass());
    }
  }

  @Override
  public void settingsTagsList() {
    binding.listTags.addItemDecoration(new SpacesItemDecoration(5));
    binding.listTags.setLayoutManager(
        new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    tagsPresenter.loadingDataTagList();
  }

  @Override
  public void setEmptyListNotes() {
    // Здесь нужно реализовать проверку на количество елементов в адаптере заметок
    binding.setEmptyNotes(true);
  }

  public void createNoteButton() {
    startActivity(new Intent(this, NoteActivity.class));
  }
}
