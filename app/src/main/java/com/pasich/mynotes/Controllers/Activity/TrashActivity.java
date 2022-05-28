package com.pasich.mynotes.Controllers.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import com.pasich.mynotes.Controllers.Dialogs.CleanTrashDialog;
import com.pasich.mynotes.Models.TrashModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.ActionUtils;
import com.pasich.mynotes.Utils.Adapters.ListNotesAdapter;
import com.pasich.mynotes.Utils.Interface.ManageTrash;
import com.pasich.mynotes.View.TrashView;
import com.pasich.mynotes.databinding.ActivityTrashBinding;

import java.util.Objects;

public class TrashActivity extends AppCompatActivity implements ManageTrash, View.OnClickListener {

  protected TrashView TrashView;
  protected TrashModel TrashModel;
  private ListNotesAdapter ListNotesAdapter;
  private ActionUtils ActionUtils;
  private ActivityTrashBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityTrashBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    TrashView = new TrashView(binding);
    TrashModel = new TrashModel(this);
    setupActionBar();
    initAdapter();
    initListener();
  }

  private void initListener() {
    binding.cleanTrash.setOnClickListener(this);
  }

  private void initAdapter() {
    ListNotesAdapter = new ListNotesAdapter(this, TrashModel.notesArray);
    binding.ListTrash.setAdapter(ListNotesAdapter);
    initActionUtils();
    ListNotesAdapter.setOnItemClickListener(
        new ListNotesAdapter.OnItemClickListener() {

          @Override
          public void onClick(int position) {
            ActionUtils.selectItemAction(position);
          }

          @Override
          public void onLongClick(int position) {}
        });
    emptyListViewUtil();
  }

  private void initActionUtils() {
    ActionUtils =
        new ActionUtils(
            binding.getRoot(), ListNotesAdapter, ConstraintSet.PARENT_ID, R.id.activity_trash);
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

  /** Method that sets up the Activity's ActionBar */
  private void setupActionBar() {
    setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
  }

  /** The method that implements the response to the activity with which the arrival was */
  private void closeActivity() {
    finish();
  }

  private void emptyListViewUtil() {
    if (ListNotesAdapter.getItemCount() == 0) {
      binding.ListTrash.setVisibility(View.GONE);
      binding.cleanTrash.setVisibility(View.GONE);
      binding.emptyListVIew.setVisibility(View.VISIBLE);
    } else {
      binding.ListTrash.setVisibility(View.VISIBLE);
      binding.cleanTrash.setVisibility(View.VISIBLE);
      binding.emptyListVIew.setVisibility(View.GONE);
    }
  }

  @Override
  public void cleanTrash() {
    TrashModel.cleanTrash();
    ListNotesAdapter.getData().clear();
    ListNotesAdapter.notifyDataSetChanged();
    emptyListViewUtil();
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == binding.cleanTrash.getId()) {
      new CleanTrashDialog().show(getSupportFragmentManager(), "CLeanTrash");
    }
  }
}
