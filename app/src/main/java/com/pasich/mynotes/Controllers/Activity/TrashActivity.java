package com.pasich.mynotes.Controllers.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.Controllers.Dialogs.CleanTrashDialog;
import com.pasich.mynotes.Models.TrashModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.ActionUtils;
import com.pasich.mynotes.Utils.Adapters.ListNotesAdapter;
import com.pasich.mynotes.Utils.Interface.ManageTrash;
import com.pasich.mynotes.Utils.SpacesItemDecoration;
import com.pasich.mynotes.databinding.ActivityTrashBinding;

import java.util.Objects;

public class TrashActivity extends AppCompatActivity implements ManageTrash, View.OnClickListener {

  protected TrashModel TrashModel;
  private ListNotesAdapter ListNotesAdapter;
  private ActionUtils ActionUtils;
  private ActivityTrashBinding binding;
  private int countDataObject;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityTrashBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    initialization();
  }

  private void initialization() {
    TrashModel = new TrashModel(this);
    setupActionBar();
    initAdapter();
    initListener();
  }

  /** Method that sets up the Activity's ActionBar */
  private void setupActionBar() {
    setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
  }

  private void initListener() {
    binding.cleanTrash.setOnClickListener(this);
    ActionUtils.getActionPanel().findViewById(R.id.removeNotesArray).setOnClickListener(this);
  }

  private void initAdapter() {
    ListNotesAdapter = new ListNotesAdapter(this, TrashModel.notesArray);
    binding.ListTrash.setAdapter(ListNotesAdapter);
    countDataObject = ListNotesAdapter.getItemCount();
    binding.ListTrash.addItemDecoration(new SpacesItemDecoration(25));
    binding.ListTrash.setLayoutManager(
        new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
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
    initActionUtils();
  }

  private void initActionUtils() {
    ActionUtils = new ActionUtils(binding.getRoot(), ListNotesAdapter, R.id.activity_trash);
    ActionUtils.addButtonToActionPanel(R.drawable.ic_restore, R.id.removeNotesArray);
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

  @SuppressLint("NotifyDataSetChanged")
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
    if (v.getId() == ActionUtils.getActionPanel().findViewById(R.id.removeNotesArray).getId()) {
      restoreNotesArray();
    }
  }

  public void restoreNotesArray() {
    for (int noteID : ActionUtils.getArrayChecked()) {
      TrashModel.notesMove(
          noteID, TrashModel.DbHelper.COLUMN_NOTES, TrashModel.DbHelper.COLUMN_TRASH);
    }
    ActionUtils.closeActionPanel();
    restartListNotes();
  }

  @SuppressLint("NotifyDataSetChanged")
  public void restartListNotes() {
    final LayoutAnimationController controller =
        AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
    binding.ListTrash.setLayoutAnimation(controller);
    ListNotesAdapter.getData().clear();
    TrashModel.getUpdateCursor();
    ListNotesAdapter.notifyDataSetChanged();
    binding.ListTrash.scheduleLayoutAnimation();
    emptyListViewUtil();
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
