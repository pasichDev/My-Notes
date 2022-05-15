package com.pasich.mynotes.Controllers.Activity;

import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Controllers.Dialogs.CleanTrashDialog;
import com.pasich.mynotes.Model.TrashModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.DefaultListAdapter;
import com.pasich.mynotes.Utils.Check.CheckFolderUtils;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.View.TrashView;

public class TrashActivity extends AppCompatActivity implements UpdateListInterface {

  protected TrashView TrashView;
  protected TrashModel TrashModel;
  private DefaultListAdapter defaultListAdapter;
  private int countItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    new CheckFolderUtils().checkSystemFolder(this.getFilesDir());
    setTheme(applyTheme(this));
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trash);

    TrashView = new TrashView(getWindow().getDecorView());
    TrashModel = new TrashModel(this);
    setupActionBar();

    if (TrashModel.getSizeArray() != 0) createAdapterListView();
    else TrashView.createViewTrashEmpty();
    countItems = TrashModel.getSizeArray();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    if (TrashModel.getSizeArray() >= 1) menu.findItem(R.id.trashClean).setVisible(true);
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
    } else if (item.getItemId() == R.id.trashClean) {
      if (!(defaultListAdapter.getCount() == 0)) {
        new CleanTrashDialog().show(getSupportFragmentManager(), "CleanTrashDialog");
      } else {
        Toast.makeText(getApplicationContext(), R.string.trashNull, Toast.LENGTH_SHORT).show();
      }
    }
    return true;
  }

  /** Method that sets up the Activity's ActionBar */
  private void setupActionBar() {
    setSupportActionBar(TrashView.toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  /** The method that implements the response to the activity with which the arrival was */
  private void closeActivity() {
    setResult(24, new Intent().putExtra("updateList", countItems != TrashModel.getSizeArray()));
    finish();
  }

  private void createAdapterListView() {
    defaultListAdapter = new DefaultListAdapter(this, R.layout.list_notes, TrashModel.notesArray);
    TrashView.trashNotesList.setAdapter(defaultListAdapter);
    //  activateListener();
  }
  /*
  private void activateListener() {
    TrashView.trashNotesList.setOnItemClickListener(
        (parent, v, position, id) ->
            new ChoiceTrashDialog(
                    new String[] {
                      String.valueOf(position), defaultListAdapter.getItem(position).getNameList()
                    })
                .show(getSupportFragmentManager(), "choiceTrash"));
  }*/

  @Override
  public void RestartListView() {
    defaultListAdapter.getData().clear();
    defaultListAdapter.notifyDataSetChanged();
    TrashView.createViewTrashEmpty();
  }

  @Override
  public void RemoveItem(int position) {
    defaultListAdapter.getData().remove(position);
    defaultListAdapter.notifyDataSetChanged();
    if (defaultListAdapter.getCount() == 0) TrashView.createViewTrashEmpty();
  }
}
