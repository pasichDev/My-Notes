package com.pasich.mynotes.Controllers.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Controllers.Dialogs.CleanTrashDialog;
import com.pasich.mynotes.Model.TrashModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.DefaultListAdapter;
import com.pasich.mynotes.Utils.Interface.ManageTrash;
import com.pasich.mynotes.View.TrashView;

import java.util.Objects;

public class TrashActivity extends AppCompatActivity implements ManageTrash {

  protected TrashView TrashView;
  protected TrashModel TrashModel;
  private DefaultListAdapter defaultListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trash);

    TrashView = new TrashView(getWindow().getDecorView());
    TrashModel = new TrashModel(this);
    setupActionBar();

    defaultListAdapter = new DefaultListAdapter(this, R.layout.list_notes, TrashModel.notesArray);
    TrashView.trashNotesList.setAdapter(defaultListAdapter);
    emptyListViewUtil();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    menu.findItem(R.id.trashCleanButton).setVisible(true);
    return true;
  }

  @Override
  public void onBackPressed() {
    closeActivity();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      closeActivity();
    }
    if (item.getItemId() == R.id.trashCleanButton) {
      new CleanTrashDialog().show(getSupportFragmentManager(), "CLeanTrash");
    }
    return true;
  }

  /** Method that sets up the Activity's ActionBar */
  private void setupActionBar() {
    setSupportActionBar(TrashView.toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
  }

  /** The method that implements the response to the activity with which the arrival was */
  private void closeActivity() {
    finish();
  }

  private void emptyListViewUtil() {
    if (defaultListAdapter.getCount() == 0) {
      TrashView.trashNotesList.setVisibility(View.GONE);
      findViewById(R.id.emptyListVIew).setVisibility(View.VISIBLE);
    } else {
      TrashView.trashNotesList.setVisibility(View.VISIBLE);
      findViewById(R.id.emptyListVIew).setVisibility(View.GONE);
    }
  }

  @Override
  public void cleanTrash() {
    TrashModel.cleanTrash();
    defaultListAdapter.getData().clear();
    defaultListAdapter.notifyDataSetChanged();
    emptyListViewUtil();
  }
}
