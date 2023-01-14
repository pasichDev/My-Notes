package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.FormattedDataUtil.lastDayEditNote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

public class NoteActivity extends BaseActivity implements NoteContract.view {

    @Inject
    public ActivityNoteBinding binding;
    @Inject
    public NoteContract.presenter notePresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        binding.setPresenter((NotePresenter) notePresenter);
        notePresenter.attachView(this);
        notePresenter.getLoadIntentData(getIntent());
        notePresenter.viewIsReady();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!notePresenter.getExitNoteSave() && binding.valueNote.getText().toString().trim().length() >= 2)
            saveNote();
    }


    @Override
    public void initParam() {

    }

    @Override
    public void initTypeActivity() {
        if (notePresenter.getNewNotesKey()) {
            activatedActivity();
            if (notePresenter.getTagNote().length() >= 2) changeTag(notePresenter.getTagNote());
            binding.titleToolbarData.setText(getString(R.string.lastDateEditNote, lastDayEditNote(new Date().getTime())));

            if (notePresenter.getShareText() != null && notePresenter.getShareText().length() > 5)
                binding.valueNote.setText(notePresenter.getShareText());
        } else if (notePresenter.getIdKey() >= 1) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            notePresenter.loadingData(notePresenter.getIdKey());
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
        binding.valueNote.addTextChangedListener(new TextWatcher() {
            @Override
            protected void changeText(Editable s) {
                autoCreatedList(s);
            }
        });

    }

    private void autoCreatedList(Editable s) {

        String[] texts = s.toString().split("\n"); //массив всех строчек
        String beforeString = texts.length > 3 ? texts[texts.length - 1] : texts[0];

        //это рабочий вариант

/*
        String string = s.toString();
        if (string.length() > 0 && string.charAt(string.length() - 1) == '\n') {
            Log.wtf(TAG, "probell: " );

            if(beforeString.substring(0, beforeString.length() >= 2 ? 1 : beforeString.length()).contains("-") && beforeString.length() == 2 ){
            //    binding.valueNote.setText(s + beforeString.replace("- ", "").trim());
            }
            else if(beforeString.substring(0, beforeString.length() >= 2 ? 1 : beforeString.length()).contains("-") ){
                Log.wtf(TAG, "yes - " );
                binding.valueNote.setText(s  + "- ");
                binding.valueNote.setSelection(binding.valueNote.length());
            }
        }
 */

    }


    @Override
    public void editIdNoteCreated(long idNote) {
        notePresenter.getNote().setId(Math.toIntExact(idNote));
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
        if (!notePresenter.getNewNotesKey())
            binding.valueNote.setSelection(binding.valueNote.getText().length());
        binding.valueNote.setFocusableInTouchMode(true);
        binding.valueNote.requestFocus();
        if (!notePresenter.getNewNotesKey()) {
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
            new MoreNoteDialog(notePresenter.getNewNotesKey() ? new Note().create(binding.notesTitle.getText().toString(), binding.valueNote.getText().toString(), new Date().getTime()) : notePresenter.getNote(), notePresenter.getNewNotesKey(), true, 0).show(getSupportFragmentManager(), "MoreNote");

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

                new LinkInfoDialog(link, type).show(getSupportFragmentManager(), "LinkInfoDialog");

            }

        });
        binding.titleToolbarData.setText(getString(R.string.lastDateEditNote, lastDayEditNote(note.getDate())));
        changeTag(note.getTag());
    }


    @Override
    public void closeNoteActivity() {

        binding.getRoot().clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.valueNote.getWindowToken(), 0);

        notePresenter.setExitNoSave(true);
        if (binding.valueNote.getText().toString().trim().length() >= 2) saveNote();
        if (notePresenter.getShareText().length() >= 2)
            Toast.makeText(this, getString(R.string.noteSaved), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void saveNote() {
        long mThisDate = new Date().getTime();
        String mTitle = binding.notesTitle.getText().toString();
        String mValue = binding.valueNote.getText().toString();

        String mNoteValue = "";

        if (!notePresenter.getNewNotesKey())
            mNoteValue = notePresenter.getNote().getValue() == null ? "" : notePresenter.getNote().getValue();

        if (notePresenter.getNewNotesKey()) {
            Note note = new Note().create(mTitle.length() >= 2 ? mTitle : "", mValue, mThisDate, notePresenter.getTagNote());
            notePresenter.setNote(note);
            notePresenter.createNote(note);
            notePresenter.setNewNoteKey(false);

        } else if (!mValue.equals(mNoteValue) || !mTitle.equals(notePresenter.getNote().getTitle())) {
            boolean x1 = false;
            if (!notePresenter.getNote().getTitle().contentEquals(mTitle)) {
                notePresenter.getNote().setTitle(mTitle);
                x1 = true;
            }
            if (!mNoteValue.contentEquals(mValue)) {
                notePresenter.getNote().setValue(mValue);
                x1 = true;
            }

            if (x1) {
                notePresenter.getNote().setDate(mThisDate);
                notePresenter.saveNote(notePresenter.getNote());
            }

        }
    }

    @Override
    public void closeActivityNotSaved() {
        notePresenter.setExitNoSave(true);
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
        binding.valueNote.setTypeface(null, notePresenter.getTypeFace(notePresenter.getDataManager().getTypeFaceNoteActivity()));
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


    @Override
    public void createShortCut() {
        onError(getString(R.string.addShortCutSuccessfully), binding.noteLayout);
    }

    @Override
    public void shortCutDouble() {
        onError(getString(R.string.shortCutCreateFallDouble), binding.noteLayout);
    }

}
