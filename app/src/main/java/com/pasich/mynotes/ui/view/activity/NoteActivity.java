package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.di.App.getApp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.di.note.NoteActivityModule;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.ui.view.dialogs.note.MoreNoteDialog;

import java.util.Objects;

import javax.inject.Inject;

public class NoteActivity extends AppCompatActivity implements NoteContract.view {

  public String shareText, tagNote;
  public int idKey;
  public boolean newNoteKey;

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

  }


  @Override
  public void init() {
    getApp()
            .getComponentsHolder()
            .getActivityComponent(getClass(), new NoteActivityModule())
            .inject(NoteActivity.this);
    binding.setPresenter((NotePresenter) notePresenter);
    this.idKey = getIntent().getIntExtra("idNote", 0);
    this.tagNote = getIntent().getStringExtra("tagNote");
    this.shareText = getIntent().getStringExtra("shareText");
    this.newNoteKey = getIntent().getBooleanExtra("NewNote", true);
  }

  @Override
  public void initTypeActivity() {
    if (newNoteKey) {
      activatedActivity();
      if (shareText != null && shareText.length() > 5)
        binding.valueNote.setText(shareText);
    } else if (idKey >= 1) {
      notePresenter.loadingData(idKey);
    }
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


  @Deprecated
  public void activatedActivity() {
    /**
     * єту шляпу нужно переписать под биндинг
     * в режимим активации
     */
    binding.notesTitle.setFocusable(true);
    binding.notesTitle.setFocusableInTouchMode(true);
    binding.valueNote.setFocusable(true);
    binding.valueNote.setFocusableInTouchMode(true);
    binding.valueNote.requestFocus();
    binding.editActive.setVisibility(View.GONE);
    binding.spechStart.setVisibility(View.VISIBLE);
  }

  private void saveNote() {
    // if (NoteModel.newNoteKey) {
    //   NoteModel.createNote();
    // }
  }

  @Override
  public void onRestart() {
    super.onRestart();
  }

  @Override
  public void onBackPressed() {
    notePresenter.closeActivity();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
    menu.findItem(R.id.moreBut).setVisible(true);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      notePresenter.closeActivity();
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
    notePresenter.detachView();
    if (isFinishing()) {
      notePresenter.destroy();
      getApp().getComponentsHolder().releaseActivityComponent(getClass());
    }
  }


  @Override
  public void loadingNote(Note note) {
    binding.notesTitle.setText(note.getTitle());
    binding.valueNote.setText(note.getValue());
    binding.dataEditNote.setText(note.getDate());
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


  @Override
  public void closeNoteActivity() {
    //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    // if (binding.valueNote.length() >= 2) {
    //   saveNote();
    //  }
    finish();
  }


}
