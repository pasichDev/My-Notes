package com.pasich.mynotes.Controllers.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.Model.NoteModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.View.NoteView;

import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

  private Menu toolbarMenu;

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
    toolbarMenu = menu;
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    menu.findItem(R.id.moreBut).setVisible(true);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {

    if (item.getItemId() == android.R.id.home) {
      closeNotesSave();
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

  /**
   * Метод который при старте активности определяет режим ее работы. @KeyFunctions = NewNote -
   * Новаяя заметка @KeyFunctions = EditNote - Редактировать сущесствующую зaметку @KeyFunctions =
   * TrashNote - Просмотр заметки из корзины
   */
  /*private void NotesMode() {
    if (KeyFunction.equals("NewNote") && idNote.equals("null")) {
      setTitle(getResources().getText(R.string.NewNote));
      notesControllers.activeEditText();
    } else if (KeyFunction.equals("EditNote") && !idNote.equals("null")) {
      setTitle(getResources().getText(R.string.EditNote));
      MyEditText.setText(fileCore.readFile(idNote, folder + "/"));
      notesControllers.deactiveEditText();
    } else if (KeyFunction.equals("TrashNote") && !idNote.equals("null")) {
      setTitle(getResources().getText(R.string.viewNotes));
      MyEditText.setText(fileCore.readFile(idNote, "trash/"));
      EditButton.setVisibility(View.GONE);
      SpeechToTextButton.setVisibility(View.GONE);
      notesControllers.deactiveEditText();
    }
  }*/

  private void closeNotesSave() {
    finish();
  }
}
