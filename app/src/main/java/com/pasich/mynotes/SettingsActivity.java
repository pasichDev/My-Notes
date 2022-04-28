package com.pasich.mynotes;

import static com.pasich.mynotes.Сore.ThemeClass.ThemeColorValue;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Adapters.TabLayout.ViewPagerAdapter;
import com.pasich.mynotes.Fragments.Prefences.FragmentAppInfo;
import com.pasich.mynotes.Fragments.Prefences.FragmentBackup;
import com.pasich.mynotes.Fragments.Prefences.FragmentMain;
import com.pasich.mynotes.Fragments.Prefences.FragmentVoice;
import com.pasich.mynotes.Сore.SystemCostant;

public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme(
        ThemeColorValue(
            PreferenceManager.getDefaultSharedPreferences(this)
                .getString("themeColor", SystemCostant.Settings_Theme)));
    setTitle(getResources().getText(R.string.settings));

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    Toolbar mActionBarToolbar = findViewById(R.id.toolbar_actionbar);
    setSupportActionBar(mActionBarToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
    ViewPager viewPager = findViewById(R.id.settingsViewPager);
    setupViewPager(viewPager);
    TabLayout tabLayout = findViewById(R.id.settingsTabLayout);
    tabLayout.setupWithViewPager(viewPager);
  }

  private void setupViewPager(ViewPager viewPager) {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new FragmentMain(), getString(R.string.mainPrefences));
    adapter.addFragment(new FragmentBackup(), getString(R.string.recoveryCopy));
    adapter.addFragment(new FragmentVoice(), getString(R.string.speechPrefences));
    adapter.addFragment(new FragmentAppInfo(), getString(R.string.infoAppPrefences));
    viewPager.setAdapter(adapter);
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
