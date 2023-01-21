package com.pasich.mynotes.base.activity;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.snackbar.Snackbar;
import com.pasich.mynotes.MyApp;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.BaseView;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.di.component.DaggerActivityComponent;
import com.pasich.mynotes.di.module.ActivityModule;
import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.pasich.mynotes.utils.themes.ThemesArray;
import com.preference.PowerPreference;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ActivityComponent activityComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectTheme();
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this)).applicationComponent(((MyApp) getApplication())
                        .getApplicationComponent()).build();
    }


    @Override
    public void selectTheme() {
        /**
         * эти переменные нельзя переносить в кинжал
         */
        boolean dynamicColorEnabled = PowerPreference.getDefaultFile().getBoolean(PreferencesConfig.ARGUMENT_PREFERENCE_DYNAMIC_COLOR, PreferencesConfig.ARGUMENT_DEFAULT_DYNAMIC_COLOR_VALUE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (dynamicColorEnabled) {
                setTheme(R.style.AppThemeDynamic);
            } else {
                Log.wtf(TAG, "SET THEME API 33 NO DYNAIMcoLORS ");
                setTheme(new ThemesArray()
                        .getThemeStyle(
                                PowerPreference
                                        .getDefaultFile()
                                        .getInt(PreferencesConfig.ARGUMENT_PREFERENCE_THEME, PreferencesConfig.ARGUMENT_DEFAULT_THEME_VALUE)
                        ));
            }

        } else {
            setTheme(new ThemesArray()
                    .getThemeStyle(
                            PowerPreference
                                    .getDefaultFile()
                                    .getInt(PreferencesConfig.ARGUMENT_PREFERENCE_THEME, PreferencesConfig.ARGUMENT_DEFAULT_THEME_VALUE)
                    ));
        }


    }

    @Override
    public void showMessage(String message) {
        if (message != null) Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resID) {
        showMessage(getString(resID));
    }

    @Override
    public void onInfo(String message, View view) {
        Snackbar.make(view == null ? findViewById(android.R.id.content) : view, message != null ? message : getString(R.string.error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int resID, View view) {
        Snackbar snackbar = Snackbar.make(view == null ? findViewById(android.R.id.content) : view, getString(resID) != null ? getString(resID) : getString(R.string.warning), Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(MaterialColors.getColor(this, R.attr.colorError, Color.DKGRAY));
        snackbar.setActionTextColor(MaterialColors.getColor(this, R.attr.colorOnError, Color.GRAY));

        snackbar.show();

    }

    @Override
    public void onInfo(int resID, View view) {
        onInfo(getString(resID), view);
    }

    @Override
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}