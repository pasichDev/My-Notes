package com.pasich.mynotes.Controllers.Activity;

import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Model.FoldersModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.View.FolderView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FolderActivity extends AppCompatActivity {


    FolderView FolderView;
    FoldersModel FoldersModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(applyTheme(this));
        setContentView(R.layout.activity_folder);

        FolderView = new FolderView(getWindow().getDecorView());
        FoldersModel = new FoldersModel(this);
        setSupportActionBar(FolderView.toolbar);

    }


    protected void createFoldersList(){
        FolderView.foldersList.setAdapter(new DefaultListAdapter(this, R.layout.list_foldes, FoldersModel.foldersArray));
    }



}