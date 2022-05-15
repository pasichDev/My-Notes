package com.pasich.mynotes.Controllers.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Controllers.Dialogs.NewTagDialog;
import com.pasich.mynotes.Model.MainModel;
import com.pasich.mynotes.R;
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
      registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {});

  private MainView MainView;
  private MainUtils MainUtils;
  private DefaultListAdapter defaultListAdapter;
  private MainModel MainModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    MainView = new MainView(getWindow().getDecorView());
    MainUtils = new MainUtils();
    MainModel = new MainModel(this);
    final SortSwitchUtils sortSwitch = new SortSwitchUtils(this, MainView.sortButton);
    final FormatSwitchUtils formatSwitch = new FormatSwitchUtils(this, MainView.formatButton);

    setSupportActionBar(MainView.toolbar);

    MainView.sortButton.setOnClickListener(v -> {});

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
              new NewTagDialog().show(getSupportFragmentManager(), "New Tab");
              Objects.requireNonNull(MainView.TabLayout.getTabAt(1)).select();
            }
          }
        });
  }

  @Override
  public void onResume() {
    super.onResume();
    while (MainModel.tags.moveToNext()) {
      MainView.TabLayout.addTab(MainView.TabLayout.newTab().setText(MainModel.tags.getString(0)));
    }

    // defaultListAdapter = new DefaultListAdapter(this, R.layout.list_notes, notesArray);
    //  MainView.ListView.setAdapter(defaultListAdapter);
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
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.setingsBut) openSettings();
    return false;
  }

  @Override
  public void addTagQuery(String tagName) {
    if (MainView.TabLayout.getTabCount() <= 10) {
      MainModel.createTag(tagName);
      MainView.TabLayout.addTab(MainView.TabLayout.newTab().setText(tagName), 2);
    } else Toast.makeText(this, getString(R.string.countTagsError), Toast.LENGTH_LONG).show();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    MainModel.closeConnection();
  }

  private void openSettings() {
    startActivity.launch(new Intent(this, SettingsActivity.class));
  }
}
