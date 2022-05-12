package com.pasich.mynotes.Controllers.Activity;

import static com.pasich.mynotes.Utils.Constants.BackConstant.UPDATE_THEME;
import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.pasich.mynotes.Adapters.VIewPage.ViewPagerAdapter;
import com.pasich.mynotes.Controllers.Fragments.Prefences.FragmentAppInfo;
import com.pasich.mynotes.Controllers.Fragments.Prefences.FragmentBackup;
import com.pasich.mynotes.Controllers.Fragments.Prefences.FragmentMain;
import com.pasich.mynotes.Controllers.Fragments.Prefences.FragmentVoice;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.UpdateTheme;
import com.pasich.mynotes.View.SettingsView;

public class SettingsActivity extends AppCompatActivity implements UpdateTheme {
  private SettingsView SettingsView;

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

  /**
   * Вообщем здесь очень большой трабл Получеться после обновления темы через recreateActivity(),
   * активность стартует с нуля и все интерфейсы и предачи данных на обратке очисчаються Что можно
   * сделать? @1 - Это изменить подход, смены темы на этой странице и потом черз интерфейс или через
   * Intent изменить тему в MainActivity @2 - Это дальше играться со статическими переменами, но это
   * конечно плохая идея
   */
  @Override
  public void recreateActivity() {
    UPDATE_THEME = true;
    finish();
    startActivity(getIntent());
    overridePendingTransition(0, 0);
  }
}
