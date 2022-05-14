package com.pasich.mynotes.Controllers.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.Controllers.Dialogs.NewTagDialog;
import com.pasich.mynotes.Controllers.Fragments.ViewPagerMain.ListNotesFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.IOnBackPressed;
import com.pasich.mynotes.Utils.MainUtils;
import com.pasich.mynotes.Utils.SwitchButton.FormatSwitchUtils;
import com.pasich.mynotes.Utils.SwitchButton.SortSwitchUtils;
import com.pasich.mynotes.View.MainView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
  public ImageButton sortButton, formatButton;

  private DefaultListAdapter defaultListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    MainView = new MainView(getWindow().getDecorView());
    MainUtils = new MainUtils();
    setSupportActionBar(MainView.toolbar);

    sortSwitch = new SortSwitchUtils(this, MainView.sortButton);
    formatSwitch = new FormatSwitchUtils(this, MainView.formatButton);
    startButtonList();

    findViewById(R.id.sortButton)
        .setOnClickListener(
            v -> {
              /*   sortSwitch.sortNote();
              FragmentListNotes.restartListNotes();*/
            });
    findViewById(R.id.formatButton)
        .setOnClickListener(
            v -> {
              formatSwitch.formatNote();
              MainView.setNotesListCountColumns();
            });

    ArrayList<ListNotesModel> notesArray = new ArrayList<>();

    notesArray.add(new ListNotesModel("Notes 1", "12.13.35", false, false));
    notesArray.add(new ListNotesModel("Notes 2", "12.13.35", false, false));

    notesArray.add(new ListNotesModel("Notes 3", "12.13.35", false, false));
    notesArray.add(new ListNotesModel("Notes 4", "12.13.35", false, false));
    notesArray.add(new ListNotesModel("Notes 5", "12.13.35", false, false));
    notesArray.add(new ListNotesModel("Notes 6", "12.13.35", false, false));
    notesArray.add(new ListNotesModel("Notes 7", "12.13.35", false, false));
    notesArray.add(new ListNotesModel("Notes 8", "12.13.35", false, false));

    defaultListAdapter = new DefaultListAdapter(this, R.layout.list_notes, notesArray);
    MainView.ListView.setAdapter(defaultListAdapter);

    MainView.TabLayout.addOnTabSelectedListener(
        new TabLayout.OnTabSelectedListener() {
          @Override
          public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
              case 0:
                new NewTagDialog().show(getSupportFragmentManager(), "New Tab");
                MainView.TabLayout.getTabAt(1).select();
            }
          }

          @Override
          public void onTabUnselected(TabLayout.Tab tab) {}

          @Override
          public void onTabReselected(TabLayout.Tab tab) {}
        });

    // new NewTagDialog().show(getSupportFragmentManager(),"New Tab");
  }

  /** Create Button List to TabPanel */
  private void startButtonList() {
    sortSwitch.getSortParam();
    formatSwitch.getFormatParam();
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

  /** Start Settings.activity */
  private void openSettings() {
    startActivity.launch(new Intent(this, SettingsActivity.class));
  }



  /**
   * Тоже очень интересная реализация Позже желательно выпилить
   * Нужно любой ценой  реализовать обновления ListView после onPause()
   * */
  @Override
  public void onStart() {
    super.onStart();

  }

}
