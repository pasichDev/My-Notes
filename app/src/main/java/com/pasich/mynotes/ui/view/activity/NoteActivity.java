package com.pasich.mynotes.ui.view.activity;

import static android.service.controls.ControlsProviderService.TAG;
import static com.pasich.mynotes.utils.FormattedDataUtil.lastDayEditNote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.base.simplifications.TextWatcher;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.ui.view.dialogs.MoreNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.note.LinkInfoDialog;
import com.pasich.mynotes.utils.CustomLinkMovementMethod;
import com.pasich.mynotes.utils.activity.NoteUtils;

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
    private long idKey;
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

    @Override
    public void initParam() {
        this.idKey = getIntent().getLongExtra("idNote", 0);
        this.tagNote = getIntent().getStringExtra("tagNote");
        if (tagNote == null) this.tagNote = "";
        this.shareText = getIntent().getStringExtra("shareText");
        if (shareText == null) this.shareText = "";
        this.newNoteKey = getIntent().getBooleanExtra("NewNote", true);

        Log.wtf(TAG, "initParam: " + idKey);
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
            if (tagNote.length() >= 2) changeTag(tagNote);
            binding.titleToolbarData.setText(getString(R.string.lastDateEditNote, lastDayEditNote(new Date().getTime())));

            if (shareText != null && shareText.length() > 5) binding.valueNote.setText(shareText);
        } else if (idKey >= 1) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
        binding.notesTitle.addTextChangedListener(null);

    }


    @Override
    public void loadingNote(Note note) {
        binding.notesTitle.setText(note.getTitle().length() >= 2 ? note.getTitle() : "");
        binding.valueNote.setText(note.getValue() == null ? "" : note.getValue());
        binding.valueNote.setMovementMethod(new CustomLinkMovementMethod() {
            @Override
            protected void onClickLink(String link, int type) {
                link = link.replaceAll("mailto:", "").replaceAll("tel:", "");

                new LinkInfoDialog(link, type).show(getSupportFragmentManager(), "LinkInfoDIalog");

            }

        });
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
            Note note = new Note().create(mTitle.length() >= 2 ? mTitle : "", mValue, mThisDate, tagNote);
            this.mNote = note;
            notePresenter.createNote(note);

            this.newNoteKey = false;

        } else if (!mValue.equals(mNote.getValue()) || !mTitle.equals(mNote.getTitle())) {
            boolean x1 = false;
            if (!mNote.getTitle().contentEquals(mTitle)) {
                mNote.setTitle(mTitle);
                x1 = true;
            }
            if (!mNote.getValue().contentEquals(mValue)) {
                mNote.setValue(mValue);
                x1 = true;
            }

            if (x1) {
                mNote.setDate(mThisDate);
                notePresenter.saveNote(mNote);
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
    public void openCopyNote(int idNote) {
        finish();
        startActivity(new Intent(NoteActivity.this, NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", idNote).putExtra("shareText", "").putExtra("tagNote", ""));
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
