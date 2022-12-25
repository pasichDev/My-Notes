package com.pasich.mynotes.ui.view.activity;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.Theme;
import com.pasich.mynotes.databinding.ActivityThemeBinding;
import com.pasich.mynotes.utils.adapters.themeAdapter.ThemesAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;
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
    }


    private void setListThemes() {
        mAdapter = new ThemesAdapter(this, getThemes());
        activityThemeBinding.themes.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activityThemeBinding.themes.addItemDecoration(new SpacesItemDecoration(10));
        activityThemeBinding.themes.setAdapter(mAdapter);
        initListeners();
    }


    private ArrayList<Theme> getThemes() {
        ArrayList<Theme> labels = new ArrayList<>();
        // labels.add(new Theme(R.drawable.theme_default, 0));
        labels.add(new Theme(R.drawable.ic_theme_green, 1));
        labels.add(new Theme(R.drawable.ic_theme_darkblue, 2));
        labels.add(new Theme(R.drawable.ic_theme_yellow, 3));
        return labels;
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
        });
    }
}