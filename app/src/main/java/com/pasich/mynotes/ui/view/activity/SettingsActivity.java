package com.pasich.mynotes.ui.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActivitySettingsBinding;
import com.pasich.mynotes.ui.view.fragments.FragmentSettings;


public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    public interface IOnBackPressed {
        boolean onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SettingsActivity.this, R.layout.activity_settings);

        setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            closeFragment(true);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        closeFragment(false);
    }

    private void closeFragment(boolean onBack) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            if (onBack) super.onBackPressed();
            finish();

        } else {
            binding.titleActivity.setText(R.string.settings);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new FragmentSettings())
                    .commit();
        }
    }

}
