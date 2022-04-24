package com.pasich.mynotes;

import static com.pasich.mynotes.Сore.Methods.MethodCheckEmptyTrash.checkCountListTrashActivity;
import static com.pasich.mynotes.Сore.Methods.checkSystemFolders.checkSystemFolder;
import static com.pasich.mynotes.Сore.ThemeClass.ThemeColorValue;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesfor;
import com.pasich.mynotes.Dialogs.ChoiseTrash;
import com.pasich.mynotes.Dialogs.CleanTrash;
import com.pasich.mynotes.Сore.ListContolers.TrashListData;
import com.pasich.mynotes.Сore.SystemCostant;

import java.util.ArrayList;

public class TrashActivity extends AppCompatActivity {

    private DefaultListAdapter defaultListAdapter;
    private ArrayList<ListNotesfor> listNotesfors;
    private int countItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkSystemFolder(this);
        setTheme(ThemeColorValue(PreferenceManager
                .getDefaultSharedPreferences(this).getString("themeColor", SystemCostant.Settings_Theme)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_notes);
        setTitle(getResources().getText(R.string.trashN));


        Toolbar mActionBarToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) { actionBar.setDisplayHomeAsUpEnabled(true); }

        GridView trashNotesList = findViewById(R.id.ListNotesTrash);
        TrashListData trashListData = new TrashListData(this);
        listNotesfors  = trashListData.newListAdapter();


        defaultListAdapter = new DefaultListAdapter(getApplicationContext(), R.layout.list_notes, listNotesfors);
        trashNotesList.setAdapter(defaultListAdapter);
        trashNotesList.setOnItemClickListener((parent, v, position, id) -> {
            ChoiseTrash dialog = new ChoiseTrash(position,listNotesfors,defaultListAdapter);
            dialog.show(getSupportFragmentManager(), "choiseTrash");
        });

        checkCountListTrashActivity(this,defaultListAdapter);
        countItems = defaultListAdapter.getCount();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
        if(defaultListAdapter.getCount()>=1)
        menu.findItem(R.id.trashClean).setVisible(true);
        return true;
    }


    @Override
    public void onBackPressed() {
        closeActivity();
    }

    @Override //Метод который звонит при каждом нажатии на тулбар
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home){
           closeActivity();
            }else
        if (item.getItemId()==R.id.trashClean){
            if(!(defaultListAdapter.getCount() == 0)) {
            CleanTrash dialog = new CleanTrash(defaultListAdapter);
            dialog.show(getSupportFragmentManager(), "CleanTrash"); }
            else{
                Toast.makeText(getApplicationContext(),
                        R.string.trashNull,
                        Toast.LENGTH_SHORT).show();

            }
        }
        return true; }


    public void closeActivity(){
           setResult(24,
                   new Intent().putExtra("updateList", countItems != defaultListAdapter.getCount()));
            finish();
        }
}