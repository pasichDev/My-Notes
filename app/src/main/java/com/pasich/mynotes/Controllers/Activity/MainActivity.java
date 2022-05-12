package com.pasich.mynotes.Controllers.Activity;

import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.pasich.mynotes.Adapters.VIewPage.ViewPagerAdapter;
import com.pasich.mynotes.Controllers.Dialogs.FolderEditAndCreateDialog;
import com.pasich.mynotes.Controllers.Fragments.ViewPagerMain.ListNotesFragment;
import com.pasich.mynotes.Controllers.Fragments.ViewPagerMain.VoiceListNotesFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.IOnBackPressed;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.Utils.MainUtils;
import com.pasich.mynotes.Utils.SwitchButton.FormatSwitchUtils;
import com.pasich.mynotes.Utils.SwitchButton.SortSwitchUtils;
import com.pasich.mynotes.View.MainView;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
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

    findViewById(R.id.sortButton)
        .setOnClickListener(
            v -> {
              sortSwitch.sortNote();
              FragmentListNotes.restartListNotes();
            });
    findViewById(R.id.formatButton)
        .setOnClickListener(
            v -> {
              formatSwitch.formatNote();
              FragmentListNotes.formatListView();
            });

    MainView.viewPager.registerOnPageChangeCallback(
        new ViewPager2.OnPageChangeCallback() {
          @Override
          public void onPageSelected(int position) {
            MainView.tabLayout.selectTab(MainView.tabLayout.getTabAt(position));
          }
        });

  }

  /** The method that sets up the ViewPager */
  private void setupViewPager() {
    this.FragmentListNotes = new ListNotesFragment();
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
    adapter.addFragment(FragmentListNotes);
    adapter.addFragment(new VoiceListNotesFragment());
    MainView.viewPager.setAdapter(adapter);
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
