package com.pasich.mynotes;

import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;
import static com.pasich.mynotes.Utils.Utils.CheckEmptyTrashUtils.checkCountListTrash;
import static com.pasich.mynotes.Utils.Utils.CheckFolderUtils.checkSystemFolder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Controllers.Dialogs.ChoiceTrashDialog;
import com.pasich.mynotes.Controllers.Dialogs.CleanTrashDialog;
import com.pasich.mynotes.Сore.ListContolers.TrashListData;

import java.util.ArrayList;

public class TrashActivity extends AppCompatActivity {

  private DefaultListAdapter defaultListAdapter;
  private ArrayList<ListNotesModel> ListNotesModel;
  private int countItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    checkSystemFolder(this);
    setTheme(applyTheme(this));
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trash_notes);
    setTitle(getResources().getText(R.string.trashN));

    Toolbar mActionBarToolbar = findViewById(R.id.toolbar_actionbar);
    setSupportActionBar(mActionBarToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    GridView trashNotesList = findViewById(R.id.ListTrash);
    TrashListData trashListData = new TrashListData(this);
    ListNotesModel = trashListData.newListAdapter();

    defaultListAdapter =
        new DefaultListAdapter(this, R.layout.list_notes, ListNotesModel);
    trashNotesList.setAdapter(defaultListAdapter);
    trashNotesList.setOnItemClickListener(
        (parent, v, position, id) -> {
          ChoiceTrashDialog dialog =
              new ChoiceTrashDialog(position, ListNotesModel, defaultListAdapter);
          dialog.show(getSupportFragmentManager(), "choiseTrash");
        });

    countItems = defaultListAdapter.getCount();
    if (countItems == 0) checkCountListTrash(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    if (defaultListAdapter.getCount() >= 1) menu.findItem(R.id.trashClean).setVisible(true);
    return true;
  }

  @Override
  public void onBackPressed() {
    closeActivity();
  }

  @Override // Метод который звонит при каждом нажатии на тулбар
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
