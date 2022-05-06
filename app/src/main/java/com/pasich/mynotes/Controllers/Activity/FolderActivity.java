package com.pasich.mynotes.Controllers.Activity;


import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.ToolBarView;
import com.pasich.mynotes.View.FolderView;

import android.os.Bundle;

public class FolderActivity extends AppCompatActivity {


    FolderView FolderView;
    ToolBarView ToolBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(applyTheme(this));
        setContentView(R.layout.activity_folder);

        FolderView = new FolderView(getWindow().getDecorView());

        setSupportActionBar(FolderView.toolbar);

        /*


        setTheme(
                ThemeColorValue(
                        PreferenceManager.getDefaultSharedPreferences(this)
                                .getString("themeColor", SystemCostant.Settings_Theme)));


        setSupportActionBar(findViewById(R.id.toolbar_actionbar));
        Toolbar mActionBarToolbar = findViewById(R.id.toolbar_actionbar);

        setSupportActionBar(mActionBarToolbar);

*/
    }





}