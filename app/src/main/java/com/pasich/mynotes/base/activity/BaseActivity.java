package com.pasich.mynotes.base.activity;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.snackbar.Snackbar;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.BaseView;
import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.pasich.mynotes.utils.constants.SnackBarInfo;
import com.pasich.mynotes.utils.themes.ThemesArray;
import com.preference.PowerPreference;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        selectTheme();
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
    public void onInfoSnack(int resID, View view, int typeInfo, int time) {
        Snackbar snackbar = Snackbar.make(view == null ? findViewById(android.R.id.content) : view, getString(resID), time);
        if (typeInfo != SnackBarInfo.Info) {
            TextView snackbarTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
            snackbarTextView.setTypeface(snackbarTextView.getTypeface(), Typeface.BOLD);
        }
        switch (typeInfo) {
            case SnackBarInfo.Info:
                break;
            case SnackBarInfo.Success:
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.successColorBackground));
                snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.successTextOnColor));
                break;
            case SnackBarInfo.Error:
                snackbar.setBackgroundTint(MaterialColors.getColor(this, R.attr.colorError, Color.DKGRAY));
                snackbar.setActionTextColor(MaterialColors.getColor(this, R.attr.colorOnError, Color.GRAY));
                break;
            default:
        }


        snackbar.show();
    }

    @Override
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}