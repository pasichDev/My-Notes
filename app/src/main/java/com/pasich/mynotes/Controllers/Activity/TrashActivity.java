package com.pasich.mynotes.Controllers.Activity;

import static com.pasich.mynotes.Utils.Theme.ThemeUtils.applyTheme;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Model.TrashModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.DefaultListAdapter;
import com.pasich.mynotes.View.TrashView;

import java.util.Objects;

public class TrashActivity extends AppCompatActivity {

  protected TrashView TrashView;
  protected TrashModel TrashModel;
  private DefaultListAdapter defaultListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    setTheme(applyTheme(this));
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trash);

    TrashView = new TrashView(getWindow().getDecorView());
    TrashModel = new TrashModel(this);
    setupActionBar();

    defaultListAdapter = new DefaultListAdapter(this, R.layout.list_notes, TrashModel.notesArray);
    TrashView.trashNotesList.setAdapter(defaultListAdapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    menu.findItem(R.id.trashCleanButton).setVisible(true);
    return true;
  }

  @Override
  public void onBackPressed() {
    closeActivity();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      closeActivity();
    }
    if (item.getItemId() == R.id.trashCleanButton) {
      closeActivity();
    }
    return true;
  }

  /** Method that sets up the Activity's ActionBar */
  private void setupActionBar() {
    setSupportActionBar(TrashView.toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
  }

  /** The method that implements the response to the activity with which the arrival was */
  private void closeActivity() {
    finish();
  }





}
