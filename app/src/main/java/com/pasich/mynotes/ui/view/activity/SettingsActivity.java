package com.pasich.mynotes.ui.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;
import com.pasich.mynotes.otherClasses.controllers.fragments.FragmentAppInfo;
import com.pasich.mynotes.otherClasses.controllers.fragments.FragmentBackup;
import com.pasich.mynotes.otherClasses.controllers.fragments.FragmentMain;
import com.pasich.mynotes.otherClasses.controllers.fragments.FragmentVoice;
import com.pasich.mynotes.otherClasses.view.SettingsView;
import com.pasich.mynotes.utils.adapters.ViewPagerAdapter;

public class SettingsActivity extends AppCompatActivity {
  private SettingsView SettingsView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    SettingsView = new SettingsView(getWindow().getDecorView());
    setSupportActionBar(SettingsView.toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    setupViewPager();
    SettingsView.viewPager.registerOnPageChangeCallback(
        new ViewPager2.OnPageChangeCallback() {
          @Override
          public void onPageSelected(int position) {
            SettingsView.tabLayout.selectTab(SettingsView.tabLayout.getTabAt(position));
          }
        });
  }

  /** The method that sets up the ViewPager */
  private void setupViewPager() {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
    adapter.addFragment(new FragmentMain());
    adapter.addFragment(new FragmentBackup());
    adapter.addFragment(new FragmentVoice());
    adapter.addFragment(new FragmentAppInfo());
    SettingsView.viewPager.setAdapter(adapter);
    SettingsView.tabLayout.addOnTabSelectedListener(
        new TabLayout.OnTabSelectedListener() {
          @Override
          public void onTabSelected(TabLayout.Tab tab) {
            SettingsView.viewPager.setCurrentItem(tab.getPosition());
          }

          @Override
          public void onTabUnselected(TabLayout.Tab tab) {}

          @Override
          public void onTabReselected(TabLayout.Tab tab) {}
        });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return true;
  }

  @Override
  public void onBackPressed() {
    finish();
  }


}
