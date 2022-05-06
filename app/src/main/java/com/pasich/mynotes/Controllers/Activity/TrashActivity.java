package com.pasich.mynotes.Controllers.Activity;

import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;
import static com.pasich.mynotes.Utils.Utils.CheckEmptyTrashUtils.checkCountListTrash;
import static com.pasich.mynotes.Utils.Utils.CheckFolderUtils.checkSystemFolder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Dialogs.ChoiceTrashDialog;
import com.pasich.mynotes.Dialogs.CleanTrashDialog;
import com.pasich.mynotes.Model.TrashModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.View.TrashView;

public class TrashActivity extends AppCompatActivity {

  private DefaultListAdapter defaultListAdapter;
  protected TrashView TrashView;
  protected TrashModel TrashModel;
  private int countItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    checkSystemFolder(this);
    setTheme(applyTheme(this));
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trash_notes);

    TrashView = new TrashView(getWindow().getDecorView());
    TrashModel = new TrashModel(this);
    setSupportActionBar(TrashView.toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
    if (TrashModel.getSizeArray() != 0) {
      defaultListAdapter = new DefaultListAdapter(this, R.layout.list_notes, TrashModel.notesArray);
      TrashView.trashNotesList.setAdapter(defaultListAdapter);

      TrashView.trashNotesList.setOnItemClickListener(
          (parent, v, position, id) -> {
            new ChoiceTrashDialog(position, TrashModel.notesArray, defaultListAdapter)
                .show(getSupportFragmentManager(), "choiseTrash");
          });
    } else checkCountListTrash(this);
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
        CleanTrashDialog dialog = new CleanTrashDialog(defaultListAdapter);
        dialog.show(getSupportFragmentManager(), "CleanTrashDialog");
      } else {
        Toast.makeText(getApplicationContext(), R.string.trashNull, Toast.LENGTH_SHORT).show();
      }
    }
    return true;
  }

  public void closeActivity() {
    setResult(24, new Intent().putExtra("updateList", countItems != defaultListAdapter.getCount()));
    finish();
  }
}
