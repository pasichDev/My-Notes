package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.di.App.getApp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.di.note.NoteActivityModule;
import com.pasich.mynotes.otherClasses.models.NoteModel;
import com.pasich.mynotes.otherClasses.view.NoteView;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.ui.view.dialogs.note.MoreNoteDialog;

import java.util.Objects;

import javax.inject.Inject;

public class NoteActivity extends AppCompatActivity implements NoteContract.view {

  private NoteView NoteVIew;
  private NoteModel NoteModel;


  @Inject
  public DataManager dataManager;
  @Inject
  public NoteContract.presenter notePresenter;

  private ActivityNoteBinding binding;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(NoteActivity.this, R.layout.activity_note);
    init();

    notePresenter.attachView(this);
    notePresenter.setDataManager(dataManager);
    notePresenter.viewIsReady();

    /*
    NoteModel = new NoteModel(this, NoteVIew = new NoteView(getWindow().getDecorView()));


    initializationActivity();

    NoteVIew.activatedButton.setOnClickListener(view -> NoteVIew.activatedActivity());

     */
  }


  @Override
  public void init() {
    getApp()
            .getComponentsHolder()
            .getActivityComponent(getClass(), new NoteActivityModule())
            .inject(NoteActivity.this);
    binding.setPresenter((NotePresenter) notePresenter);
  }

  @Override
  public void initListeners() {

  }

  @Override
  public void settingsActionBar() {
    setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

  }

  /**
   * Метод который
   */
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

  private void saveNote() {
    if (NoteModel.newNoteKey) {
      NoteModel.createNote();
    }
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
  public void onDestroy() {
    super.onDestroy();
    //  NoteModel.closeConnection();
  }


  private void closeNotesSave() {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    if (NoteVIew.valueNote.length() >= 2) {
      //   saveNote();
    }
    finish();
  }


  @Override
  public void settingsEditTextNote(String textStyle) {
    int styleSel;
    switch (textStyle) {
      case "italic":
        styleSel = Typeface.ITALIC;
        break;
      case "bold":
        styleSel = Typeface.BOLD;
        break;
      case "bold-italic":
        styleSel = Typeface.BOLD_ITALIC;
        break;
      default:
        styleSel = Typeface.NORMAL;
        break;
    }
    binding.valueNote.setTypeface(null, styleSel);
  }

  @Override
  public void textSizeValueNote(int sizeText) {
    binding.valueNote.setTextSize(sizeText == 0 ? 16 : sizeText);
  }
}
