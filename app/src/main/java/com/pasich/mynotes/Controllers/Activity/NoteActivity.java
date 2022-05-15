package com.pasich.mynotes.Controllers.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Controllers.Dialogs.MoreNoteDialog;
import com.pasich.mynotes.Model.NoteModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.View.NoteView;

import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

  private NoteView NoteVIew;
  private NoteModel NoteModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_note);
    NoteVIew = new NoteView(getWindow().getDecorView());
    NoteModel = new NoteModel(this);

    setSupportActionBar(findViewById(R.id.toolbar_actionbar));
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    initializationActivity();

    NoteVIew.activatedButton.setOnClickListener(view -> NoteVIew.activatedActivity());
  }

  private void initializationActivity() {
    if (NoteModel.newNoteKey) {
      NoteVIew.activatedActivity();
      if (NoteModel.shareText != null && NoteModel.shareText.length() > 5)
        NoteVIew.valueNote.setText(NoteModel.shareText);
    } else if (NoteModel.idKey >= 1) {
      noteEdit();
    }
  }

  private void noteEdit() {
    NoteModel.queryNote();
    NoteModel.cursorNote.moveToFirst();
    NoteVIew.titleName.setText(NoteModel.cursorNote.getString(1));
    NoteVIew.valueNote.setText(NoteModel.cursorNote.getString(2));
    NoteVIew.editDate.setText(NoteModel.cursorNote.getString(3));
  }

  @Override
  public void onRestart() {
    super.onRestart();
  }

  @Override
  public void onBackPressed() {
    closeNotesSave();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    menu.findItem(R.id.moreBut).setVisible(true);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {

    if (item.getItemId() == android.R.id.home) {
      closeNotesSave();
    }
    if (item.getItemId() == R.id.moreBut) {
      new MoreNoteDialog().show(getSupportFragmentManager(), "moreNote");
    }
    return true;
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private void closeNotesSave() {
    finish();
  }
}
