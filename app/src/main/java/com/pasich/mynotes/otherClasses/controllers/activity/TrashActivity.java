package com.pasich.mynotes.otherClasses.controllers.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.interfaces.ManageTrash;
import com.pasich.mynotes.databinding.ActivityTrashBinding;
import com.pasich.mynotes.otherClasses.controllers.dialog.CleanTrashDialog;
import com.pasich.mynotes.otherClasses.models.TrashModel;
import com.pasich.mynotes.utils.adapters.NotesAdapter;
import com.pasich.mynotes.utils.other.ActionUtils;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.Objects;

public class TrashActivity extends AppCompatActivity implements ManageTrash, View.OnClickListener {

  protected TrashModel TrashModel;
  private NotesAdapter ListNotesAdapter;
  private ActionUtils ActionUtils;
  private ActivityTrashBinding binding;
  private int countDataObject;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityTrashBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    setupActionBar();
    TrashModel = new TrashModel(this);
    binding.cleanTrash.setOnClickListener(this);
    binding.ListTrash.addItemDecoration(new SpacesItemDecoration(25));
  }

  @Override
  public void onResume() {
    super.onResume();
    if (!TrashModel.getDb().isOpen()) TrashModel.getRecreateDb();
    if (ListNotesAdapter == null) initAdapter();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    TrashModel.closeDB();
  }

  /** Method that sets up the Activity's ActionBar */
  private void setupActionBar() {
    setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
  }

  private void initAdapter() {
    ListNotesAdapter = new NotesAdapter(TrashModel.notesArray);
    binding.ListTrash.setAdapter(ListNotesAdapter);
    countDataObject = ListNotesAdapter.getItemCount();
    ListNotesAdapter.setOnItemClickListener(
        new NotesAdapter.OnItemClickListener() {

          @Override
          public void onClick(int position) {

            ActionUtils.selectItemAction(position);
          }

          @Override
          public void onLongClick(int position) {}
        });
    binding.setEmptyNotesTrash(ListNotesAdapter.getData().isEmpty());
    initActionUtils();
  }

  private void initActionUtils() {
    ActionUtils = new ActionUtils(binding.getRoot(), ListNotesAdapter, R.id.activity_trash);
    ActionUtils.addButtonToActionPanel(R.drawable.ic_restore, R.id.removeNotesArray);
    ActionUtils.getActionPanel().findViewById(R.id.removeNotesArray).setOnClickListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
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
    return true;
  }

  @SuppressLint("NotifyDataSetChanged")
  @Override
  public void cleanTrash() {
    TrashModel.cleanTrash();
    ListNotesAdapter.notifyDataSetChanged();
    binding.setEmptyNotesTrash(ListNotesAdapter.getData().isEmpty());
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == binding.cleanTrash.getId()) {
      new CleanTrashDialog().show(getSupportFragmentManager(), "CLeanTrash");
      ActionUtils.closeActionPanel();
    }
    if (v.getId() == ActionUtils.getActionPanel().findViewById(R.id.removeNotesArray).getId()) {
      restoreNotesArray();
    }
  }

  private void restoreNotesArray() {
    for (long noteID : ActionUtils.getArrayChecked()) {
      ListNotesAdapter.removeItemsArray((int) noteID);
      TrashModel.notesMove(
          (int) noteID, TrashModel.DbHelper.COLUMN_NOTES, TrashModel.DbHelper.COLUMN_TRASH);
    }
    ActionUtils.closeActionPanel();
    binding.setEmptyNotesTrash(ListNotesAdapter.getData().isEmpty());
  }

  /** The method that implements the response to the activity with which the arrival was */
  private void closeActivity() {
    Intent intent = new Intent();
    if (countDataObject != ListNotesAdapter.getItemCount()) {
      intent.putExtra("RestartListView", true);
    }
    intent.putExtra("tagNote", "");
    setResult(44, intent);
    finish();
  }
}
