package com.pasich.mynotes.ui.view.activity;


import static com.pasich.mynotes.utils.constants.Backup_Constants.FILE_NAME_BACKUP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.databinding.ActivityBackupBinding;
import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.ui.presenter.BackupPresenter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;

public class BackupActivity extends BaseActivity implements BackupContract.view {

    @Inject
    public BackupContract.presenter presenter;

    @Inject
    public ActivityBackupBinding binding;

    private final ActivityResultLauncher<Intent> startIntentExport =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        ParcelFileDescriptor descriptor;
                        try {
                            descriptor = getContentResolver().openFileDescriptor(result.getData().getData(), "w");
                            FileOutputStream outputStream = new FileOutputStream(descriptor.getFileDescriptor());
                            outputStream.write(presenter.getJsonBackup().getBytes());
                            outputStream.close();
                            descriptor.close();
                            onError(R.string.creteLocalCopyOkay, binding.activityBackup);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        presenter.attachView(this);
        presenter.viewIsReady();
        binding.setPresenter((BackupPresenter) presenter);
    }

    @Override
    public void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void createBackupLocal() {
        startIntentExport.launch(new Intent(Intent.ACTION_CREATE_DOCUMENT).addCategory(Intent.CATEGORY_OPENABLE).setType("application/json").putExtra(Intent.EXTRA_TITLE, FILE_NAME_BACKUP));
    }

    @Override
    public void createBackupCloud() {


    }
}