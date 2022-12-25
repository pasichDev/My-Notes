package com.pasich.mynotes.ui.view.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.Theme;
import com.pasich.mynotes.databinding.ActivityThemeBinding;
import com.pasich.mynotes.utils.adapters.themeAdapter.ThemesAdapter;
import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.utils.themesUtils.ThemesArray;
import com.preference.PowerPreference;

import java.util.Objects;

import javax.inject.Inject;

public class ThemeActivity extends BaseActivity {

    @Inject
    public ActivityThemeBinding activityThemeBinding;
    private ThemesAdapter mAdapter;
    private int themeIdStartActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        themeIdStartActivity = PowerPreference.getDefaultFile().getInt(PreferencesConfig.ARGUMENT_PREFERENCE_THEME, PreferencesConfig.ARGUMENT_DEFAULT_THEME_VALUE);

        setSupportActionBar(activityThemeBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setListThemes();
        initFunctions();
    }


    private void initFunctions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            activityThemeBinding.dynamicColor.setVisibility(View.VISIBLE);
            activityThemeBinding.dynamicColor.setChecked(PowerPreference.getDefaultFile().getBoolean(PreferencesConfig.ARGUMENT_PREFERENCE_DYNAMIC_COLOR, PreferencesConfig.ARGUMENT_DEFAULT_DYNAMIC_COLOR_VALUE));
        }
    }

    private void setListThemes() {
        mAdapter = new ThemesAdapter(this, new ThemesArray().getThemes(), themeIdStartActivity);
        activityThemeBinding.themes.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activityThemeBinding.themes.addItemDecoration(new SpacesItemDecoration(10));
        activityThemeBinding.themes.setAdapter(mAdapter);
        initListeners();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finishActivity();
        }

        return true;
    }


    private void finishActivity() {
        Theme mTheme = mAdapter.getSelectTheme();
        if (themeIdStartActivity != mTheme.getId()) {
            setResult(11, new Intent().putExtra("updateThemeId", mAdapter.getSelectTheme().getId()));
        }
        finish();
    }

    @Override
    public void initListeners() {
        mAdapter.setSelectLabelListener(position -> {
            Theme theme = mAdapter.getThemes().get(position);
            mAdapter.selectTheme(position);
            PowerPreference.getDefaultFile().setInt(PreferencesConfig.ARGUMENT_PREFERENCE_THEME, theme.getId());
            redrawActivity(theme);
        });
        activityThemeBinding.dynamicColor.setOnCheckedChangeListener((buttonView, isChecked) -> PowerPreference.getDefaultFile().setBoolean(PreferencesConfig.ARGUMENT_PREFERENCE_DYNAMIC_COLOR, isChecked));
    }


    private void redrawActivity(Theme mTheme) {
        //install theme
        setTheme(mTheme.getTHEME_STYLE());
        int colorOnBackground = MaterialColors.getColor(this, R.attr.colorOnBackground, Color.GRAY);
        int colorBackground = MaterialColors.getColor(this, android.R.attr.colorBackground, Color.GRAY);
        activityThemeBinding.activityTheme.setBackgroundColor(colorBackground);
        activityThemeBinding.titleTheme.setTextColor(colorOnBackground);
        activityThemeBinding.titleFunc.setTextColor(colorOnBackground);


    }

}