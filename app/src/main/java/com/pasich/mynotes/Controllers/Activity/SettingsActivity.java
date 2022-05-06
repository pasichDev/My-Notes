package com.pasich.mynotes.Controllers.Activity;

import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Adapters.TabLayout.ViewPagerAdapter;
import com.pasich.mynotes.Controllers.Fragments.Prefences.FragmentAppInfo;
import com.pasich.mynotes.Controllers.Fragments.Prefences.FragmentBackup;
import com.pasich.mynotes.Controllers.Fragments.Prefences.FragmentMain;
import com.pasich.mynotes.Controllers.Fragments.Prefences.FragmentVoice;
import com.pasich.mynotes.R;
import com.pasich.mynotes.View.SettingsView;

public class SettingsActivity extends AppCompatActivity {
  protected SettingsView SettingsView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme(applyTheme(this));
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    SettingsView = new SettingsView(getWindow().getDecorView());
    setSupportActionBar(SettingsView.toolbar);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    setupViewPager();
  }

  /** The method that sets up the ViewPager */
  private void setupViewPager() {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new FragmentMain(), getString(R.string.mainPrefences));
    adapter.addFragment(new FragmentBackup(), getString(R.string.recoveryCopy));
    adapter.addFragment(new FragmentVoice(), getString(R.string.speechPrefences));
    adapter.addFragment(new FragmentAppInfo(), getString(R.string.infoAppPrefences));
    SettingsView.viewPager.setAdapter(adapter);
    SettingsView.tabLayout.setupWithViewPager(SettingsView.viewPager);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      closeSettings();
    }
    return true;
  }

  @Override
  public void onBackPressed() {
    closeSettings();
  }

  protected void closeSettings() {
    finish();
  }

  @Override
  public void onResume() {
    super.onResume();
  }
}
