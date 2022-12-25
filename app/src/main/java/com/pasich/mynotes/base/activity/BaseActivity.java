package com.pasich.mynotes.base.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.pasich.mynotes.MyApp;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.di.component.DaggerActivityComponent;
import com.pasich.mynotes.di.module.ActivityModule;
import com.pasich.mynotes.utils.ThemesArray;
import com.pasich.mynotes.utils.constants.PreferencesConfig;
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


      /*  if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setTheme(R.style.AppThemeDynamic);
            DynamicColors.applyToActivitiesIfAvailable(getApplication());

        } else {

       */
        setTheme(new ThemesArray()
                .getThemeStyle(
                        PowerPreference
                                .getDefaultFile()
                                .getInt(PreferencesConfig.ARGUMENT_PREFERENCE_THEME, PreferencesConfig.ARGUMENT_DEFAULT_THEME_VALUE)
                ));
        //  }
        // On Dynamic Color
        // DynamicColors.applyToActivitiesIfAvailable(getApplication());
        //  setTheme(R.style.AppThemeDynamic);
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
    public void onError(String message, View view) {
        if (message != null) showSnackbar(message, view);
        else showSnackbar(getString(R.string.error), view);
    }

    @Override
    public void onError(int resID, View view) {
        onError(getString(resID), view);
    }


    private void showSnackbar(String message, View view) {
        Snackbar snackbar = Snackbar.make(view == null ? findViewById(android.R.id.content) : view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}