package com.pasich.mynotes.Controllers.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Controllers.Dialogs.NewTagDialog;
import com.pasich.mynotes.Model.Adapter.ListNotesModel;
import com.pasich.mynotes.Model.MainModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.ActionUtils;
import com.pasich.mynotes.Utils.Adapters.DefaultListAdapter;
import com.pasich.mynotes.Utils.Interface.AddTag;
import com.pasich.mynotes.Utils.MainUtils;
import com.pasich.mynotes.Utils.Simplifications.TabLayoutListenerUtils;
import com.pasich.mynotes.Utils.SwitchButtons.FormatSwitchUtils;
import com.pasich.mynotes.Utils.SwitchButtons.SortSwitchUtils;
import com.pasich.mynotes.View.MainView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AddTag {

  /** Processing the received response from running activities */
  protected ActivityResultLauncher<Intent> startActivity =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            Log.wtf("pasic", "requestOk");
            Intent data = result.getData();
            if (result.getResultCode() == 24 && result.getData() != null) {
              Log.wtf("pasic", "codeOk");
              if (data.getBooleanExtra("updateList", false)) restartListNotes();
            }
          });

  private MainView MainView;
  private MainUtils MainUtils;
  private DefaultListAdapter defaultListAdapter;
  private MainModel MainModel;
  private ActionUtils ActionUtils;
  private SortSwitchUtils sortSwitch;
  private FormatSwitchUtils formatSwitch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initialization();
    setSupportActionBar(MainView.toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

    defaultListAdapter = new DefaultListAdapter(this, R.layout.list_notes, MainModel.notesArray);
    MainView.ListView.setAdapter(defaultListAdapter);

    MainView.sortButton.setOnClickListener(
        v -> {
          restartListNotes();
          sortSwitch.sortNote();
        });
    MainView.formatButton.setOnClickListener(
        v -> {
          formatSwitch.formatNote();
          MainView.setNotesListCountColumns();
        });

    MainView.TabLayout.addOnTabSelectedListener(
        new TabLayoutListenerUtils() {
          @Override
          public void listener(TabLayout.Tab Tab) {
            if (Tab.getPosition() == 0) {
              if (MainView.TabLayout.getTabCount() <= 10) {
                new NewTagDialog().show(getSupportFragmentManager(), "New Tab");
                Objects.requireNonNull(MainView.TabLayout.getTabAt(1)).select();
              } else
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.countTagsError),
                        Toast.LENGTH_LONG)
                    .show();
            }
          }
        });

    findViewById(R.id.newNotesButton).setOnClickListener(this::createNotesButton);

    MainView.ListView.setOnItemClickListener(
        (parent, v, position, id) -> {
          if (!ActionUtils.getAction()) openNote(defaultListAdapter.getItem(position).getId());
          else selectedItemAction(defaultListAdapter.getItem(position));
        });
    MainView.ListView.setOnItemLongClickListener(
        (arg0, arg1, position, id) -> {
          if (!ActionUtils.getAction()) {
            ActionUtils.setAction(true);
            selectedItemAction(defaultListAdapter.getItem(position));
            MainView.activateActionPanel();
          }
          return true;
        });

    while (MainModel.tags.moveToNext()) {
      MainView.TabLayout.addTab(MainView.TabLayout.newTab().setText(MainModel.tags.getString(0)));
    }
  }

  public void restartListNotes() {
    defaultListAdapter.getData().clear();
    MainModel.getUpdateCursor();
    defaultListAdapter.notifyDataSetChanged();
    Log.wtf("pasic", "restartOk");
  }

  private void selectedItemAction(ListNotesModel item) {
    if (item.getChecked()) {
      item.setChecked(false);
      item.getView().setBackground(getDrawable(R.drawable.item_note_background));
      if (defaultListAdapter.getCountChecked() == 0) {
        MainView.deactivationActionPanel();
        ActionUtils.setAction(false);
        defaultListAdapter.setChekClean();
      }
    } else {
      item.setChecked(true);
      item.getView().setBackground(getDrawable(R.drawable.item_note_background_selected));
    }
  }

  private void initialization() {
    MainView = new MainView(getWindow().getDecorView());
    MainUtils = new MainUtils();
    MainModel = new MainModel(this);
    ActionUtils = new ActionUtils();
    sortSwitch = new SortSwitchUtils(this, MainView.sortButton);
    formatSwitch = new FormatSwitchUtils(this, MainView.formatButton);
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onBackPressed() {
    MainUtils.CloseApp(MainActivity.this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    menu.findItem(R.id.setingsBut).setVisible(true);
    menu.findItem(R.id.searchBut).setVisible(true);
    menu.findItem(R.id.trashButton).setVisible(true);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.setingsBut) openSettings();
    if (item.getItemId() == R.id.trashButton) openTrash();
    return false;
  }

  @Override
  public void addTagQuery(String tagName) {
      MainModel.createTag(tagName);
      MainView.TabLayout.addTab(MainView.TabLayout.newTab().setText(tagName), 2);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    MainModel.closeConnection();
  }

  private void openSettings() {
    startActivity.launch(new Intent(this, SettingsActivity.class));
  }

  /** Start Trash.activity */
  private void openTrash() {
    startActivity.launch(new Intent(this, TrashActivity.class));
  }

  private void createNotesButton(View view) {
    if (view.getId() == R.id.newNotesButton)
      startActivity.launch(new Intent(this, NoteActivity.class).putExtra("NewNote", true));
  }

  private void openNote(int id) {
    startActivity.launch(
        new Intent(this, NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", id));
  }
}
