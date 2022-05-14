package com.pasich.mynotes.Controllers.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Controllers.Dialogs.FolderEditAndCreateDialog;
import com.pasich.mynotes.Controllers.Fragments.ViewPagerMain.ListNotesFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.IOnBackPressed;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.Utils.MainUtils;
import com.pasich.mynotes.Utils.SwitchButton.FormatSwitchUtils;
import com.pasich.mynotes.Utils.SwitchButton.SortSwitchUtils;
import com.pasich.mynotes.View.MainView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UpdateListInterface {

  private ListNotesFragment FragmentListNotes;
  /** Processing the received response from running activities */
  protected ActivityResultLauncher<Intent> startActivity =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            Intent data = result.getData();
            if (result.getResultCode() == 24 && result.getData() != null) {
              if (data.getBooleanExtra("updateList", false)) FragmentListNotes.restartListNotes();
            }

          });

  private SortSwitchUtils sortSwitch;
  private FormatSwitchUtils formatSwitch;
  private MainView MainView;
  private MainUtils MainUtils;

  private DefaultListAdapter defaultListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    MainView = new MainView(getWindow().getDecorView());
    MainUtils = new MainUtils();
    setSupportActionBar(MainView.toolbar);

    ArrayList<ListNotesModel> notesArray = new ArrayList<>();

    notesArray.add(new ListNotesModel("Notes 1", "12.13.35", false, false));
    notesArray.add(new ListNotesModel("Notes 2", "12.13.35", false, false));

    notesArray.add(new ListNotesModel("Notes 3", "12.13.35", false, false));
    notesArray.add(new ListNotesModel("Notes 4", "12.13.35", false, false));

    defaultListAdapter = new DefaultListAdapter(this, R.layout.list_notes, notesArray);
    MainView.ListView.setAdapter(defaultListAdapter);
  }


  @Override
  public void onBackPressed() {
    if (FragmentListNotes == null || !((IOnBackPressed) FragmentListNotes).onBackPressed())
      MainUtils.CloseApp(MainActivity.this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    menu.findItem(R.id.setingsBut).setVisible(true);
    menu.findItem(R.id.searchBut).setVisible(true);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.setingsBut) openSettings();

    return false;
  }

  /** Start FolderOption.Dialog */
  private void openFolderOption() {
    new FolderEditAndCreateDialog("").show(getSupportFragmentManager(), "newFolder");
  }

  /** Start Trash.activity */
  private void openTrash() {
    startActivity.launch(new Intent(this, TrashActivity.class));
  }

  /** Start Settings.activity */
  private void openSettings() {
    startActivity.launch(new Intent(this, SettingsActivity.class));
  }

  /** Create Button List to TabPanel */
  private void startButtonList() {
    sortSwitch.getSortParam();
    formatSwitch.getFormatParam();
  }

  /**
   * Тоже очень интересная реализация Позже желательно выпилить
   * Нужно любой ценой  реализовать обновления ListView после onPause()
   * */
  @Override
  public void onStart() {
    super.onStart();

  }

  @Override
  public void RestartListView() {
    FragmentListNotes.restartListNotes();
  }



  @Override
  public void RemoveItem(int position) {
    FragmentListNotes.removeItems(position);
  }
}
