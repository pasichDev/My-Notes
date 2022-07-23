package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.di.App.getApp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.trash.TrashNote;
import com.pasich.mynotes.databinding.ActivityTrashBinding;
import com.pasich.mynotes.di.trash.TrashActivityModule;
import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.presenter.TrashPresenter;
import com.pasich.mynotes.ui.view.dialogs.trash.CleanTrashDialog.CleanTrashDialog;
import com.pasich.mynotes.utils.adapters.NotesAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class TrashActivity extends AppCompatActivity implements TrashContract.view {

  private ActivityTrashBinding binding;

  private NotesAdapter notesAdapter;
  @Inject public TrashContract.presenter trashPresenter;
  @Inject public DataManager dataManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(TrashActivity.this, R.layout.activity_trash);
    init();

    trashPresenter.attachView(this);
    trashPresenter.setDataManager(dataManager);
    trashPresenter.viewIsReady();

  }

  @Override
  public void init() {
    getApp()
            .getComponentsHolder()
            .getActivityComponent(getClass(), new TrashActivityModule())
            .inject(TrashActivity.this);
    setupActionBar();
    binding.setPresenter((TrashPresenter) trashPresenter);
  }

  @Override
  public void initListeners() {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    trashPresenter.detachView();
    if (isFinishing()) {
      trashPresenter.destroy();
      getApp().getComponentsHolder().releaseActivityComponent(getClass());
    }
  }

  /** Method that sets up the Activity's ActionBar */
  private void setupActionBar() {
    setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
  }




  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    return true;
  }

  @Override
  public void onBackPressed() {
    finish();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
    finish();
    }
    return true;
  }


  @Override
  public void settingsNotesList(int countColumn, LiveData<List<TrashNote>> noteList) {
    binding.ListTrash.addItemDecoration(new SpacesItemDecoration(15));
    binding.ListTrash.setLayoutManager(
            new StaggeredGridLayoutManager(countColumn, LinearLayoutManager.VERTICAL));
    notesAdapter = new NotesAdapter(new NotesAdapter.noteDiff());
    binding.ListTrash.setAdapter(notesAdapter);
   /* noteList.observe(
            this,
        //    notes -> { notesAdapter.submitList(notes);
              });*/
  }

  @Override
  public void cleanTrashDialogShow() {
    new CleanTrashDialog().show(getSupportFragmentManager(), "CLeanTrash");
  }
}
