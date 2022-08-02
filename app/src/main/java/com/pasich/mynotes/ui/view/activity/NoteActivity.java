package com.pasich.mynotes.ui.view.activity;

import static android.speech.SpeechRecognizer.isRecognitionAvailable;
import static com.pasich.mynotes.di.App.getApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.interfaces.AudioPermission;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.di.note.NoteActivityModule;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.ui.view.dialogs.note.MoreNoteDialog;
import com.pasich.mynotes.utils.ListNotesUtils;
import com.pasich.mynotes.utils.PermissionManager;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

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

    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
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
    public void createSpeechRecognizer(String speechLanguage, String speechOutput) {
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                .putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE,
                        speechLanguage.equals("default")
                                ? Locale.getDefault()
                                : speechOutput)
                .putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initListeners() {
        if (isRecognitionAvailable(getApplicationContext())) {
            binding.spechStart.setOnTouchListener(
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
        speechRecognizer.setRecognitionListener(
                new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle bundle) {
                    }

                    @Override
                    public void onBeginningOfSpeech() {
                        //     speechStartText.setVisibility(View.VISIBLE);
                        //       imageSpeechVolume.setVisibility(View.VISIBLE);
                        //      // Эта функция начинает запис текста
                        //      speechStartText.setText(getString(R.string.spechToListen));
                    }

                    @Override
                    public void onRmsChanged(float v) {
                       /*     if (v > 7) {
                                findViewById(R.id.imageSpechVolume).setAlpha(1);
                            } else if (v > 4 && v < 7) {
                                findViewById(R.id.imageSpechVolume).setAlpha((float) 0.8);
                            } else if (v > 0 && v < 4) {
                                findViewById(R.id.imageSpechVolume).setAlpha((float) 0.5);
                            } else if (v < 0) {
                                findViewById(R.id.imageSpechVolume).setAlpha((float) 0.0);
                            }*/
                    }

                    @Override
                    public void onBufferReceived(byte[] bytes) {
                    }

                    @Override
                    public void onEndOfSpeech() {
                    }

                    @Override
                    public void onError(int i) {
                         /*   Log.d("xxx", String.valueOf(i));
                            if (i == 7) {
                                speechStartText.setVisibility(View.GONE);
                                imageSpeechVolume.setVisibility(View.GONE);
                                Toast.makeText(
                                                getApplicationContext(), getString(R.string.emptySpeec), Toast.LENGTH_SHORT)
                                        .show();
                            }
                            if (i == 2) {
                                speechStartText.setVisibility(View.GONE);
                                imageSpeechVolume.setVisibility(View.GONE);
                                Toast.makeText(
                                                getApplicationContext(),
                                                getString(R.string.errorInternetConectSpeech),
                                                Toast.LENGTH_LONG)
                                        .show();
                                speechRecognizer.stopListening();
                            }*/
                    }

                    @Override
                    public void onResults(Bundle bundle) {
                        //     ArrayList<String> data =
                        //              bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        //      saveSpeechToText(data);
                        //      speechStartText.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPartialResults(Bundle bundle) {
                    }

                    @Override
                    public void onEvent(int i, Bundle bundle) {
                    }
                });
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
        InputMethodManager imm =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.valueNote, InputMethodManager.SHOW_FORCED);
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
            speechRecognizer.destroy();
            speechRecognizer = null;
            speechRecognizerIntent = null;
        }
    }


    @Override
    public void loadingNote(Note note) {
        binding.notesTitle.setText(note.getTitle());
        binding.valueNote.setText(note.getValue());
        binding.dataEditNote.setText(note.getDate());
        this.mNote = note;
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (binding.valueNote.getText().toString().trim().length() >= 2) {
            saveNote();
        }
        finish();
    }

    private void saveNote() {
        String mThisDate = ListNotesUtils.returnDateFile(Calendar.getInstance().getTime());
        String mTitle = binding.notesTitle.getText().toString();
        String mValue = binding.valueNote.getText().toString();
        if (newNoteKey) {
            notePresenter.createNote(new Note().create(mTitle,
                    mValue,
                    mThisDate
            ));
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
                /**
                 * Сюда добавить диалог ошибки разрешенеия на запись
                 */
                //  new PermissionErrorDialog("Audio").show(getSupportFragmentManager(), "permissonError");
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


}
