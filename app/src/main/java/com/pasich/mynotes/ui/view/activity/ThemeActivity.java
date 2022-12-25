package com.pasich.mynotes.ui.view.activity;


import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.databinding.ActivityThemeBinding;
import com.pasich.mynotes.utils.ThemesArray;
import com.pasich.mynotes.utils.adapters.themeAdapter.ThemesAdapter;
import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.preference.PowerPreference;

import java.util.Objects;

import javax.inject.Inject;

public class ThemeActivity extends BaseActivity {

    @Inject
    public ActivityThemeBinding activityThemeBinding;
    private ThemesAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        setSupportActionBar(activityThemeBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setListThemes();
        initFunctions();
    }


    private void initFunctions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            activityThemeBinding.dynamicColor.setVisibility(View.VISIBLE);
        }
    }

    private void setListThemes() {
        mAdapter = new ThemesAdapter(this,
                new ThemesArray().getThemes(),
                PowerPreference.getDefaultFile()
                        .getInt(PreferencesConfig.ARGUMENT_PREFERENCE_THEME, PreferencesConfig.ARGUMENT_DEFAULT_THEME_VALUE));
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
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public void initListeners() {
        mAdapter.setSelectLabelListener(position -> {
            mAdapter.selectTheme(position);
            PowerPreference.getDefaultFile()
                    .setInt(PreferencesConfig.ARGUMENT_PREFERENCE_THEME,
                            mAdapter.getThemes().get(position).getId());
        });
    }


}