package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.FormattedDataUtil.lastDayEditNote;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.ui.view.dialogs.MoreNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.error.PermissionsError;
import com.pasich.mynotes.ui.view.dialogs.note.SourceNoteDialog;
import com.pasich.mynotes.utils.SearchSourceNote;
import com.pasich.mynotes.utils.activity.NoteUtils;
import com.pasich.mynotes.utils.base.simplifications.TextWatcher;

import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

public class NoteActivity extends BaseActivity implements NoteContract.view {

    @Inject
    public ActivityNoteBinding binding;
    @Inject
    public NoteContract.presenter notePresenter;
    @Inject
    public NoteUtils noteUtils;

    private String shareText, tagNote;
    private int idKey;
    private Note mNote;
    private boolean exitNoSave = false, newNoteKey;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        binding.setPresenter((NotePresenter) notePresenter);
        notePresenter.attachView(this);
        notePresenter.viewIsReady();

    }

    /**
     * Метод для поддержки віреза экрана нужно использовать вместе с параметром в theme.xml
     */
    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }


    @Override
    public void initParam() {
        this.idKey = getIntent().getIntExtra("idNote", 0);

        this.tagNote = getIntent().getStringExtra("tagNote");
        if (tagNote == null) this.tagNote = "";
        this.shareText = getIntent().getStringExtra("shareText");
        if (shareText == null) this.shareText = "";
        this.newNoteKey = getIntent().getBooleanExtra("NewNote", true);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!exitNoSave && binding.valueNote.getText().toString().trim().length() >= 2) saveNote();
    }


    @Override
    public void initTypeActivity() {
        if (newNoteKey) {
            activatedActivity();
            if (shareText != null && shareText.length() > 5) binding.valueNote.setText(shareText);
        } else if (idKey >= 1) {
            notePresenter.loadingData(idKey);
        }
    }


    @Override
    public void initListeners() {

        binding.notesTitle.addTextChangedListener(new TextWatcher() {
            @Override
            protected void changeText(Editable s) {

                if (s.toString().contains("\n")) {
                    binding.notesTitle.setText(s.toString().replace('\n', ' ').trim());
                    binding.valueNote.requestFocus();
                }
            }
        });


    }


    @Override
    public void loadingSourceNote() {
        SearchSourceNote searchSourceNote = new SearchSourceNote(binding.valueNote.getText().toString());
        if (searchSourceNote.getCountSource() >= 1)
            new SourceNoteDialog(searchSourceNote).show(getSupportFragmentManager(), "SourcesNoteDialog");
        else Toast.makeText(this, getString(R.string.notSource), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void editIdNoteCreated(long idNote) {
        this.mNote.setId(Math.toIntExact(idNote));
    }


    @Override
    public void settingsActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public void activatedActivity() {
        binding.setActivateEdit(true);
        binding.valueNote.setEnabled(true);
        binding.valueNote.setFocusable(true);
        if (!newNoteKey) binding.valueNote.setSelection(binding.valueNote.getText().length());
        binding.valueNote.setFocusableInTouchMode(true);
        binding.valueNote.requestFocus();
        if (!newNoteKey) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(binding.valueNote.getApplicationWindowToken(), InputMethodManager.SHOW_IMPLICIT, 0);
        }

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
        getMenuInflater().inflate(R.menu.menu_activity_toolbar_note, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            notePresenter.closeActivity();
        }
        if (item.getItemId() == R.id.moreBut) {

            new MoreNoteDialog(newNoteKey ? new Note().create(binding.notesTitle.getText().toString(), binding.valueNote.getText().toString(), new Date().getTime()) : mNote, newNoteKey, true, 0).show(getSupportFragmentManager(), "MoreNote");

        }


        return true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        notePresenter.detachView();
        if (isFinishing()) {
            notePresenter.destroy();
            binding.notesTitle.addTextChangedListener(null);
        }
    }


    @Override
    public void loadingNote(Note note) {
        binding.notesTitle.setText(note.getTitle().length() >= 2 ? note.getTitle() : "");
        binding.valueNote.setText(note.getValue());
        binding.titleToolbarData.setText(getString(R.string.lastDateEditNote, lastDayEditNote(note.getDate())));
        changeTag(note.getTag());
        this.mNote = note;

    }


    @Override
    public void closeNoteActivity() {

        binding.getRoot().clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.valueNote.getWindowToken(), 0);

        exitNoSave = true;
        if (binding.valueNote.getText().toString().trim().length() >= 2) saveNote();
        if (shareText.length() >= 2)
            Toast.makeText(this, getString(R.string.noteSaved), Toast.LENGTH_SHORT).show();

        finish();
    }

    private void saveNote() {
        long mThisDate = new Date().getTime();
        String mTitle = binding.notesTitle.getText().toString();
        String mValue = binding.valueNote.getText().toString();
        if (newNoteKey) {

            Note note = new Note().create(mTitle.length() >= 2 ? mTitle : " ", mValue, mThisDate, tagNote);
            this.mNote = note;
            notePresenter.createNote(note);


            this.newNoteKey = false;
        } else if (!mValue.equals(mNote.getValue()) || !mTitle.equals(mNote.getTitle())) {

            if (!mNote.getTitle().contentEquals(mTitle)) mNote.setTitle(mTitle);
            if (!mNote.getValue().contentEquals(mValue)) {
                mNote.setValue(mValue);
                mNote.setDate(mThisDate);
            }

            notePresenter.saveNote(mNote);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 22) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                new PermissionsError().show(getSupportFragmentManager(), "permissionError");
            }
        }
    }


    @Override
    public void closeActivityNotSaved() {
        exitNoSave = true;
        finish();
    }

    @Override
    public void changeTag(String nameTag) {
        if (nameTag.length() >= 1) {
            binding.titleToolbarTag.setText(getString(R.string.tagHastag, nameTag));
            binding.titleToolbarTag.setVisibility(View.VISIBLE);
        } else {
            binding.titleToolbarTag.setVisibility(View.GONE);
        }
    }


    @Override
    public void changeTextStyle() {
        binding.valueNote.setTypeface(null, noteUtils.getTypeFace(notePresenter.getDataManager().getTypeFaceNoteActivity()));
    }

    @Override
    public void changeTextSizeOnline(int sizeText) {
        binding.valueNote.setTextSize(sizeText == 0 ? 16 : sizeText);
        binding.notesTitle.setTextSize(sizeText == 0 ? 20 : sizeText + 4);
    }

    @Override
    public void changeTextSizeOffline() {
        changeTextSizeOnline(notePresenter.getDataManager().getSizeTextNoteActivity());
    }


}
