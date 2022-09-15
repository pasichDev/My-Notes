package com.pasich.mynotes.ui.view.activity;

import static android.speech.SpeechRecognizer.isRecognitionAvailable;
import static com.pasich.mynotes.di.App.getApp;
import static com.pasich.mynotes.utils.FormattedDataUtil.lastDayEditNote;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_STYLE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_STYLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.di.note.NoteActivityModule;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.ui.view.dialogs.note.MoreNewNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.note.MoreNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.note.PermissionsError;
import com.pasich.mynotes.ui.view.dialogs.note.SourceNoteDialog;
import com.pasich.mynotes.utils.SearchSourceNote;
import com.pasich.mynotes.utils.activity.NoteUtils;
import com.pasich.mynotes.utils.permissionManager.AudioPermission;
import com.pasich.mynotes.utils.permissionManager.PermissionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class NoteActivity extends AppCompatActivity implements NoteContract.view, AudioPermission {

    private String shareText, tagNote;
    private int idKey;
    private boolean newNoteKey;
    private Note mNote;

    @Inject
    public DataManager dataManager;
    @Inject
    public NoteContract.presenter notePresenter;
    @Inject
    public PermissionManager permissionManager;
    @Inject
    public NoteUtils noteUtils;


    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
    private ActivityNoteBinding binding;
    private boolean exitNoSave = false;

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
        this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
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
    public void createSpeechRecognizer() {
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault())
                .putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initListeners() {
        if (isRecognitionAvailable(getApplicationContext())) {
            binding.speechStart.setOnTouchListener(
                    (view, motionEvent) -> {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            speechRecognizer.stopListening();
                        }
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            if (permissionManager.checkPermissionVoice(this)) {
                                speechRecognizer.startListening(speechRecognizerIntent);
                            }
                        }
                        return false;
                    });
        }


    }

    @Override
    public void initListenerSpeechRecognizer() {
        speechRecognizer.setRecognitionListener(new com.pasich.mynotes.utils.base.simplifications.SpeechRecognizer() {
            @Override
            public void startListener() {
                binding.recordMessges.setVisibility(View.VISIBLE);
                binding.recordMessges.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_add_record_information));

            }

            @Override
            public void errorDebug(int i) {
                binding.recordMessges.setVisibility(View.GONE);
                binding.recordMessges.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_add_record_information_reverse));

                int textError = R.string.error;
                if (i == 7)
                    textError = R.string.emptySpeec;
                else if (i == 2)
                    textError = R.string.errorInternetConectSpeech;
                Toast.makeText(
                                getApplicationContext(),
                                getString(textError),
                                Toast.LENGTH_LONG)
                        .show();
                speechRecognizer.stopListening();

            }

            @Override
            public void saveText(Bundle bundle) {
                binding.recordMessges.setVisibility(View.GONE);
                binding.recordMessges.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_add_record_information_reverse));
                saveSpeechToText(bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));

            }
        });

    }

    @Override
    public void loadingSourceNote() {
        SearchSourceNote searchSourceNote = new SearchSourceNote(binding.valueNote.getText().toString());
        if (searchSourceNote.getCountSource() >= 1)
            new SourceNoteDialog(searchSourceNote).show(getSupportFragmentManager(), "SourcesNoteDialog");
        else
            Toast.makeText(this, getString(R.string.notSource), Toast.LENGTH_SHORT).show();


    }


    @SuppressLint("SetTextI18n")
    private void saveSpeechToText(ArrayList<String> result) {
        binding.valueNote.setText(binding.valueNote.getText().toString() + " " + result.get(0));
        binding.valueNote.setSelection(binding.valueNote.getText().toString().length());
    }


    @Override
    public void settingsActionBar() {
        setSupportActionBar(binding.toolbarActionbar.toolbarActionbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public void activatedActivity() {
        binding.setActivateEdit(true);
        binding.valueNote.setSelection(binding.valueNote.getText().length());
        binding.valueNote.setFocusableInTouchMode(true);
        binding.valueNote.requestFocus();

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
            if (newNoteKey) new MoreNewNoteDialog(new Note()
                    .create(binding.notesTitle.getText().toString(),
                            binding.valueNote.getText().toString(),
                            new Date().getTime())).show(getSupportFragmentManager(), "MoreNote");
            else
                new MoreNoteDialog(mNote).show(getSupportFragmentManager(), "MoreNote");
        }

        if (item.getItemId() == R.id.hiddenActionPanel) {
            manageActionPanel(item);
        }
        return true;
    }

    private void manageActionPanel(MenuItem item) {
        if (binding.actionPanel.getVisibility() == View.VISIBLE) {
            binding.actionPanel.setVisibility(View.GONE);
            item.setIcon(R.drawable.ic_panel);
        } else {
            binding.actionPanel.setVisibility(View.VISIBLE);
            item.setIcon(R.drawable.ic_panel_hidden);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (!exitNoSave && binding.valueNote.getText().toString().trim().length() >= 2) saveNote();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notePresenter.detachView();
        if (isFinishing()) {
            notePresenter.destroy();
            getApp().getComponentsHolder().releaseActivityComponent(getClass());
            speechRecognizer.destroy();
            speechRecognizer = null;
            speechRecognizerIntent = null;
        }
    }


    @Override
    public void loadingNote(Note note) {
        binding.notesTitle.setText(note.getTitle().length() >= 2 ? note.getTitle() : "");
        binding.valueNote.setText(note.getValue());
        binding.toolbarActionbar.titleToolbarData.setText(getString(R.string.lastDateEditNote, lastDayEditNote(note.getDate())));
        if (note.getTag().length() >= 1) {
            binding.toolbarActionbar.titleToolbarTag.setText(note.getTag());
        } else {
            binding.toolbarActionbar.titleToolbarTag.setVisibility(View.GONE);
        }
        this.mNote = note;

    }




    @Override
    public void closeNoteActivity() {
        if (binding.valueNote.getText().toString().trim().length() >= 2) {
            Log.wtf("pasic", "closeNoteActivity: null " + binding.valueNote.getText().toString() + "///");
            saveNote();
        }
        finish();
    }

    private void saveNote() {
        long mThisDate = new Date().getTime();
        String mTitle = binding.notesTitle.getText().toString();
        String mValue = binding.valueNote.getText().toString();
        if (newNoteKey) {
            Note note = new Note().create(mTitle.length() >= 2 ? mTitle : " ",
                    mValue,
                    mThisDate,
                    "note",
                    tagNote
            );

            try {
                this.mNote = notePresenter.loadingNote((int) notePresenter.createNote(note));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            this.newNoteKey = false;
        } else if (!mValue.equals(mNote.getValue())
                || !mTitle.equals(mNote.getTitle())) {

            if (!mNote.getTitle().contentEquals(mTitle)) mNote.setTitle(mTitle);
            if (!mNote.getValue().contentEquals(mValue)) {
                mNote.setValue(mValue);
                mNote.setDate(mThisDate);
            }

            notePresenter.saveNote(mNote);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 22) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                new PermissionsError().show(getSupportFragmentManager(), "permissionError");
            }
        }
    }


    @Override
    public void openShouldShowRequestPermissionRationale(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(permission);
        }
    }

    @Override
    public void openRequestPermissions(@NonNull String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 22);
        }
    }


    @Override
    public void deleteNote(Note note) {
        notePresenter.deleteNote(note);
        finish();
    }

    @Override
    public void closeActivityNotSaved() {
        exitNoSave = true;
        finish();
    }


    @Override
    public void changeTextStyle() {
        binding.valueNote.setTypeface(null, noteUtils.getTypeFace(
                dataManager.getDefaultPreference().getString(ARGUMENT_PREFERENCE_TEXT_STYLE, ARGUMENT_DEFAULT_TEXT_STYLE)));
    }

    @Override
    public void changeTextSizeOnline(int sizeText) {
        binding.valueNote.setTextSize(sizeText == 0 ? 16 : sizeText);
        binding.notesTitle.setTextSize(sizeText == 0 ? 20 : sizeText + 4);
    }

    @Override
    public void changeTextSizeOffline() {
        changeTextSizeOnline(dataManager.getDefaultPreference().getInt(ARGUMENT_PREFERENCE_TEXT_SIZE, ARGUMENT_DEFAULT_TEXT_SIZE));
    }

}
