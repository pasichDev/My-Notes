package com.pasich.mynotes.Controllers.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Controllers.Dialogs.CleanTrashDialog;
import com.pasich.mynotes.Models.Adapter.NoteItemModel;
import com.pasich.mynotes.Models.TrashModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.ActionUtils;
import com.pasich.mynotes.Utils.Adapters.ListNotesAdapter;
import com.pasich.mynotes.Utils.Interface.ManageTrash;
import com.pasich.mynotes.View.TrashView;

import java.util.Objects;

public class TrashActivity extends AppCompatActivity implements ManageTrash {

  protected TrashView TrashView;
  protected TrashModel TrashModel;
  private ListNotesAdapter ListNotesAdapter;
  private ActionUtils ActionUtils;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trash);

    TrashView = new TrashView(getWindow().getDecorView());
    TrashModel = new TrashModel(this);
    setupActionBar();

    ListNotesAdapter = new ListNotesAdapter(this, TrashModel.notesArray);
    TrashView.trashNotesList.setAdapter(ListNotesAdapter);
    ActionUtils = new ActionUtils(getWindow().getDecorView(), ListNotesAdapter);
    emptyListViewUtil();
    setListener();
  }

  private void setListener() {
    ListNotesAdapter.setOnItemClickListener(
        new ListNotesAdapter.OnItemClickListener() {

          @Override
          public void onClick(int position) {
            selectedItemAction(position);
          }

          @Override
          public void onLongClick(int position) {}
        });
  }

  private void selectedItemAction(int item) {
    NoteItemModel noteItem = ListNotesAdapter.getItem(item);
    if (noteItem.getChecked()) {
      noteItem.setChecked(false);
      ActionUtils.isCheckedItemFalse();
    } else {
      ActionUtils.isCheckedItem();
      noteItem.setChecked(true);
    }
    ActionUtils.manageActionPanel();
    ListNotesAdapter.notifyDataSetChanged();
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
    if (ListNotesAdapter.getItemCount() == 0) {
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
    ListNotesAdapter.getData().clear();
    ListNotesAdapter.notifyDataSetChanged();
    emptyListViewUtil();
  }
}
