package com.pasich.mynotes;

import static com.pasich.mynotes.Сore.Methods.ListNotesClass.sortIndToInt;
import static com.pasich.mynotes.Сore.Methods.checkSystemFolders.checkSystemFolder;
import static com.pasich.mynotes.Сore.SystemCostant.settingsFileName;
import static com.pasich.mynotes.Сore.ThemeClass.ThemeColorValue;
import static com.pasich.mynotes.Сore.backConstant.UPDATE_LISTVIEW;
import static com.pasich.mynotes.Сore.backConstant.UPDATE_THEME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Adapters.SpinnerNotes.SortSpinnerAdapter;
import com.pasich.mynotes.Adapters.TabLayout.ViewPagerAdapter;
import com.pasich.mynotes.Fragments.ViewPagerMain.FragmentListNotes;
import com.pasich.mynotes.Fragments.ViewPagerMain.FragmentListNotesVoice;
import com.pasich.mynotes.Сore.Interface.IOnBackPressed;
import com.pasich.mynotes.Сore.SystemCostant;


public class MainActivity extends AppCompatActivity {
    private FragmentListNotes FragmentListNotes;
    private int swipe = 0;
    private boolean onCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkSystemFolder(this);
        setTheme(ThemeColorValue(PreferenceManager
                .getDefaultSharedPreferences(this).getString("themeColor", SystemCostant.Settings_Theme)));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar_actionbar));

        if(!onCreate) {
            FragmentListNotes = new FragmentListNotes().newInstance(true);
            ViewPager viewPager = findViewById(R.id.viewpager);
            TabLayout tabLayout = findViewById(R.id.tabModeMain);
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
          //  createSpinnerSort();
        }

        onCreate = true;
    }

    /**
     * Тоже очень интересная реализация
     * Позже желательно изменить
     */
    @Override
    public void onStart(){
        super.onStart();
        if(UPDATE_THEME){
            finish();
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        UPDATE_THEME = false; }
        if(UPDATE_LISTVIEW){
            FragmentListNotes.restartListNotes();
            UPDATE_LISTVIEW = false;
        }
    }



    /**
     * Метод который настраивает ViewPager
     * @param viewPager - ссыдка на элемент ViewPager
     * @addFragment - добавляет воагмент в существующий ViewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentListNotes, getString(R.string.notes));
        adapter.addFragment(new FragmentListNotesVoice(), getString(R.string.viceNotes));
        viewPager.setAdapter(adapter);
    }


    /**
     * Обработаем нажатие назад
     */
    @Override
    public void onBackPressed() {
        if (FragmentListNotes == null ||
                !((IOnBackPressed) FragmentListNotes).onBackPressed())
        exitApp();
    }

    /**
     * Разметка тулбара
     * @param menu - ---
     * @return - true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
        menu.findItem(R.id.setingsBut).setVisible(true);
        menu.findItem(R.id.trashBut).setVisible(true);
        menu.findItem(R.id.addFolder).setVisible(true);
        return true;
    }

    /**
     * Отследить нажатия на кнопки тулбара
     * @param item - элемент на который было произведено нажатие
     * @return - если действие сработало на это активносте возвращаем true,
     * если в фрагментах то false
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.setingsBut:
               openSettings();
                return true;
            case R.id.trashBut:
                openTrash();
                return true;
            case R.id.addFolder:
                return false;
        }
        return false;
    }


    /**
     * Обработка полученого ответа от запущеных активностей
     */
    ActivityResultLauncher<Intent> startActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (data == null) {return;}
                switch (result.getResultCode()){
                    case 24:
                        if(data.getBooleanExtra("updateList", false)){
                            FragmentListNotes.restartListNotes();
                        }
                        break;

                }

            });


    /**
     * ЗАпуск активности Trash
     */
    private void openTrash() {
        Intent intent = new Intent(this, TrashActivity.class);
        startActivity.launch(intent);
    }
    /**
     * Запуск активности Settings
     */
    private void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }


    /**
     * Метод для обработки выход из приложения
     */
    private void exitApp(){
        boolean exitToSwipeTap = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("swipeToExit", SystemCostant.Settings_SwipeToExit);
        if (exitToSwipeTap) {
            swipe = swipe + 1;
            if(swipe==1){
                Toast.makeText(getApplicationContext(),
                        getString(R.string.exitWhat),
                        Toast.LENGTH_SHORT).show();
            }else
            if(swipe==2){
                finish();
                swipe = 0;
            }
        } else { finish();
        }
    }


    /**
     * Метод который инициализирует SpinnerSort
     */
  /*  private void createSpinnerSort(){
        Spinner spinner = findViewById(R.id.MainActivitySpinner);
        SortSpinnerAdapter customAdapter = new SortSpinnerAdapter(this,getResources().getStringArray(R.array.spinner_sort_name));
        spinner.setAdapter(customAdapter);
        spinner.setSelection(sortIndToInt(PreferenceManager
                .getDefaultSharedPreferences(this).getString("sortPref", SystemCostant.Settings_Theme)),false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                SharedPreferences.Editor editor = getSharedPreferences(settingsFileName, Context.MODE_PRIVATE).edit()
                .putString("sortPref", getResources().getStringArray(R.array.spinner_sort_value)[selectedItemPosition]);
                editor.apply();
                FragmentListNotes.restartListNotes();

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

*/



    public void formatNotes (View v){

    }
}