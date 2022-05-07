package com.pasich.mynotes.Controllers.Activity;

import static com.pasich.mynotes.Utils.Check.CheckFolderUtils.checkSystemFolder;
import static com.pasich.mynotes.Utils.Constants.BackConstant.UPDATE_LISTVIEW;
import static com.pasich.mynotes.Utils.Constants.BackConstant.UPDATE_THEME;
import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Adapters.TabLayout.ViewPagerAdapter;
import com.pasich.mynotes.Controllers.Fragments.ViewPagerMain.FragmentListNotesVoice;
import com.pasich.mynotes.Controllers.Fragments.ViewPagerMain.ListNotesFragment;
import com.pasich.mynotes.Dialogs.FolderOptionDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.IOnBackPressed;
import com.pasich.mynotes.Utils.MainUtils;
import com.pasich.mynotes.Utils.SwitchButtonMain.FormatSwitchUtils;
import com.pasich.mynotes.Utils.SwitchButtonMain.SortSwitchUtils;
import com.pasich.mynotes.View.MainView;

public class MainActivity extends AppCompatActivity {

  protected ListNotesFragment FragmentListNotes;
  protected SortSwitchUtils sortSwitch;
  protected FormatSwitchUtils formatSwitch;
  protected MainView MainView;
  protected MainUtils MainUtils;

  /** Processing the received response from running activities */
  protected ActivityResultLauncher<Intent> startActivity =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            Intent data = result.getData();
            if (result.getResultCode() == 24 && result.getData() != null) {
              if (data.getBooleanExtra("updateList", false))
                FragmentListNotes.restartListNotes(FragmentListNotes.getSelectFolder());
            }
          });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    checkSystemFolder(this);
    setTheme(applyTheme(this));
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    MainView = new MainView(getWindow().getDecorView());
    MainUtils = new MainUtils();
    sortSwitch = new SortSwitchUtils(this, MainView.sortButton);
    formatSwitch = new FormatSwitchUtils(this, MainView.formatButton);

    setSupportActionBar(MainView.toolbar);
    startButtonList();
    setupViewPager();

    MainView.onCreate = true;

    findViewById(R.id.sortButton)
        .setOnClickListener(
            v -> {
              sortSwitch.sortNote();
              FragmentListNotes.restartListNotes(FragmentListNotes.getSelectFolder());
            });
    findViewById(R.id.formatButton)
        .setOnClickListener(
            v -> {
              formatSwitch.formatNote();
              FragmentListNotes.formatListView();
            });
  }

  /** The method that sets up the ViewPager */
  private void setupViewPager() {
    if (!MainView.onCreate) {
      this.FragmentListNotes = new ListNotesFragment();
      ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
      adapter.addFragment(FragmentListNotes, getString(R.string.notes));
      adapter.addFragment(new FragmentListNotesVoice(), getString(R.string.viceNotes));
      MainView.viewPager.setAdapter(adapter);
      MainView.tabLayout.setupWithViewPager(MainView.viewPager);
    }
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
    menu.findItem(R.id.trashBut).setVisible(true);
    menu.findItem(R.id.addFolder).setVisible(true);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.setingsBut) openSettings();
    else if (item.getItemId() == R.id.trashBut) openTrash();
    else if (item.getItemId() == R.id.addFolder) openFolderOption();

    return false;
  }

  /** Start FolderOption.Dialog */
  private void openFolderOption() {
    new FolderOptionDialog("").show(getSupportFragmentManager(), "newFolder");
  }

  /** Start Trash.activity */
  private void openTrash() {
    startActivity.launch(new Intent(this, TrashActivity.class));
  }

  /** Start Settings.activity */
  private void openSettings() {
    startActivity(new Intent(this, SettingsActivity.class));
  }

  /** Create Button List to TabPanel */
  private void startButtonList() {
    sortSwitch.getSortParam();
    formatSwitch.getFormatParam();
  }

  /** Тоже очень интересная реализация Позже желательно выпилить */
  @Override
  public void onStart() {
    super.onStart();

    if (UPDATE_THEME) {
      finish();
      startActivity(getIntent());
      overridePendingTransition(0, 0);
      UPDATE_THEME = false;
    } else if (UPDATE_LISTVIEW) {
      FragmentListNotes.restartListNotes(FragmentListNotes.getSelectFolder());
      UPDATE_LISTVIEW = false;
    }
  }
}
