package com.pasich.mynotes;

import static android.speech.SpeechRecognizer.isRecognitionAvailable;
import static com.pasich.mynotes.Сore.Methods.shareNotesMethod.shareNotes;
import static com.pasich.mynotes.Сore.NoteControler.NotesX.closeKeyboard;
import static com.pasich.mynotes.Сore.ThemeClass.ThemeColorValue;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.Dialogs.PermissionError;
import com.pasich.mynotes.Сore.File.FileCore;
import com.pasich.mynotes.Сore.NoteControler.NotesX;
import com.pasich.mynotes.Сore.SystemCostant;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    private final int REQUEST_AUDIO_PERMISSION_RESULT = 22;
    private NotesX notesControler;
    private final FileCore fileCore = new FileCore(this);
    private String KeyFunction,folder,idNote, settingsSpechLaung, settingsSpechOutput;
    private StringBuilder textToFile;
    private ImageButton EditButton,SpechToTextButton;
    private EditText MyEditText;
    private boolean settingsAutoSave, exitToButton;
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
    private TextView spechStartText;
    private ImageView imageSpechVolume;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeColorValue(PreferenceManager
                .getDefaultSharedPreferences(this).getString("themeColor", SystemCostant.Settings_Theme)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(getResources().getText(R.string.NewNote));

        Toolbar mActionBarToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Установим переменные
        createVariable();
        notesControler =  new NotesX(getApplicationContext(),MyEditText,EditButton,KeyFunction,SpechToTextButton);
        notesControler.SetTextSize(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()).getInt("textSize", SystemCostant.Settings_TextSize));
        notesControler.setStyleText(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()).getString("textStyle", SystemCostant.Settings_TextStyle));
        checkToTextNote();
        NotesMode();

        if(isRecognitionAvailable(getApplicationContext())){
            initializateSpechToTextMethod();
            SpechToTextButton.setOnTouchListener((view, motionEvent) -> {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    speechRecognizer.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    checkPermission();
                }
                return false;
            });  }
    }


    /**
     * Метод восстановдения заметки после  остановки активности на паузу
     */
    @Override
    public void onRestart(){
        super.onRestart();
        notesControler.activeEditText();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
    }
    /**
     * Слушаетель действия назад
     * Использует метод CloseNoteSave для сохранения заметки
     */
    @Override
    public void onBackPressed() {
        closeNotesSave(settingsAutoSave,true);
    }

    /**
     * Вызов разметки ToolBar
     * Имеет 2 режима, режим создания новой заметки или редактирования старой
     * Определяем режим, и грузим нужную разметку
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
        if(KeyFunction.equals("NewNote") || KeyFunction.equals("EditNote")){
          /*  if(!KeyFunction.equals("NewNote")){
                menu.findItem(R.id.shareBut).setVisible(true);
                menu.findItem(R.id.deleteBut).setVisible(true);}*/
            menu.findItem(R.id.applyBut).setVisible(true);
            menu.findItem(R.id.noSave).setVisible(true);
        }else if(KeyFunction.equals("TrashNote")){
            menu.findItem(R.id.applyBut).setVisible(false);
        }
        return true;
    }

    /**
     * Слушатель нажатий на ToolBar
     * @param item - элемент на который сделано нажатие
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.applyBut){
            closeNotesSave(true,true);
        }
        if(item.getItemId()==android.R.id.home){
            closeNotesSave(settingsAutoSave,true);
        }
      /*  if (item.getItemId() == R.id.shareBut) {

        }
        if (item.getItemId() == R.id.deleteBut) {
            fileCore.transferNotes(idNote,"trash",folder);
            closeNotesSave(false,false);
        }*/
        if(item.getItemId() == R.id.noSave) {
            closeNotesSave(false,true);
        }
        return true; }

    /**
     * Остановка активити, метод для сохранения заметки
     */
    @Override
    public void onStop() {
        super.onStop();

        if(settingsAutoSave && !exitToButton){
            if(KeyFunction.equals("NewNote") || KeyFunction.equals("EditNote") &&
                    !textToFile.toString().equals(valueTextEdit().toString()) ) {
                CreateNotes(valueTextEdit());
                if(KeyFunction.equals("NewNote")){
                    editNotesMode();
                }
            }

            notesControler.deactiveEditText();
            checkToTextNote();
        }

        closeKeyboard(this);

    }

    /**
     * Убиваем активити и  вместе с ней службу SpeechRecognizer
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }


    /**
     * Получим ответ от метода checkPermmission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_AUDIO_PERMISSION_RESULT) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                new PermissionError("Audio").show(getSupportFragmentManager(),"permissonError");
            }
        }
    }

    /**
     *  Метод которые присваивает переменым значения.
     *  Вызывать один раз в OnCreate
     */
    private void createVariable(){

        KeyFunction = getIntent().getStringExtra("KeyFunction");
        idNote = getIntent().getStringExtra("idNote");
        folder = getIntent().getStringExtra("folder");
        MyEditText = findViewById(R.id.newNotesTextInput);
        spechStartText = findViewById(R.id.spechStartText);
        EditButton = findViewById(R.id.editNotesButton);
        SpechToTextButton = findViewById(R.id.spechTextNote);
        imageSpechVolume = findViewById(R.id.imageSpechVolume);
        settingsAutoSave = PreferenceManager
                .getDefaultSharedPreferences(this).getBoolean("autoSave", SystemCostant.Settings_AutoSave);
        settingsSpechLaung = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()).getString("spechLaunguage", SystemCostant.Settings_SpeechLanguage);
        settingsSpechOutput = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()).getString("setSpechOutputText", SystemCostant.Settings_SpeechOutput);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
    }

    /**
     *  OnClick для EditButton
     */
    public void activeButtonEditText(View view) {
        notesControler.activeEditText();
    }

    /**
     * Метод которые записывает исходный текст для заметки!
     * В некоторых случаях нужно перевызвать для обновления исходного текста
     */
    protected void checkToTextNote(){
        textToFile =  fileCore.readFile(idNote,folder+"/");
    }

    /**
     * Метод который при старте активности определяет режим ее работы.
     * @KeyFunctions = NewNote - Новаяя заметка
     * @KeyFunctions  = EditNote  - Редактировать сущесствующую зaметку
     * @KeyFunctions  = TrashNote - Просмотр заметки из корзины
     *
     */
    private void NotesMode(){
        if(KeyFunction.equals("NewNote") && idNote.equals("null")){
            setTitle(getResources().getText(R.string.NewNote));
            notesControler.activeEditText(); }
        else  if(KeyFunction.equals("EditNote") && !idNote.equals("null")){
            setTitle(getResources().getText(R.string.EditNote));
            MyEditText.setText(fileCore.readFile(idNote,folder+"/"));
            notesControler.deactiveEditText(); }
        else if(KeyFunction.equals("TrashNote") && !idNote.equals("null")){
            setTitle(getResources().getText(R.string.viewNotes));
            MyEditText.setText(fileCore.readFile(idNote,"trash/"));
            notesControler.deactiveEditText();
        }

    }

    /**
     * Метод который получает значениие из EditText
     */
    private StringBuilder valueTextEdit(){
        return new StringBuilder().append(MyEditText.getText().toString());
    }

    /**
     * Метод который сохраняет заметку, или создает новую в зависимости от режима работы
     */
    public void CreateNotes(StringBuilder result){
        if(result != null && !result.toString().trim().isEmpty()) {
            if(KeyFunction.equals("NewNote")){
                fileCore.writeNote(result,folder+"/");
            }
            if(KeyFunction.equals("EditNote") && !idNote.equals("null")){
                fileCore.editFile(result,idNote,folder+"/");
            }
        }
    }

    /**
     * mode - Отправлять результат для обновления listView
     * mode2 - Сохранять заметку
     */
    private void closeNotesSave(boolean mode, boolean mode2){

        exitToButton = true;
        Intent intent = new Intent();
        if(!mode){
            intent.putExtra("checkUpdate", "no");
            if(!mode2){
                intent.putExtra("checkUpdate", "yes");
                intent.putExtra("FOLDER", folder.length()==0 ? "" : folder);
            }
        }else if(KeyFunction.equals("NewNote") || KeyFunction.equals("EditNote") &&
                !textToFile.toString().equals(valueTextEdit().toString()) ) {
            CreateNotes(valueTextEdit());
            intent.putExtra("checkUpdate", "yes");
            intent.putExtra("FOLDER", folder.length()==0 ? "" : folder);
        }else {
            intent.putExtra("checkUpdate", "no");
        }
        setResult(RESULT_OK, intent);
        finish();
    }


    /**
     * Метод для записи полученого текста от синтезатора речи в EditText
     * @param result - передедим результат работы синтезатора
     */
    @SuppressLint("SetTextI18n")
    private void saveSpeechToText(ArrayList<String> result) {
        String valueLine = settingsSpechOutput.equals("line") ? " " : MyEditText.getText().length()==0 ? "" :"\n";
        MyEditText.setText(MyEditText.getText() + valueLine + result.get(0));
        MyEditText.setSelection(MyEditText.getText().length()); //Отобразим курсор на последнем слове

    }


    /**
     * Метод который проверяет разрешения на запись аудио
     * Внутри метода, если разрешение есть то запускаем инициализацию голсоового ввода
     */
    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) ==
                    PackageManager.PERMISSION_GRANTED) {
                speechRecognizer.startListening(speechRecognizerIntent);
            } else {
                shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO);
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_AUDIO_PERMISSION_RESULT);
            }
        }else { speechRecognizer.startListening(speechRecognizerIntent);}
    }


    private void editNotesMode(){
        //нужно заменить idNote,KeyFunction
        KeyFunction = "EditNote";
        idNote = fileCore.cleanName(String.valueOf(valueTextEdit()))+".txt";
        setTitle(getResources().getText(R.string.EditNote));
    }


    /**
     * Метод который инициализует голосоввой ввод и работает с ним
     */
    private void initializateSpechToTextMethod(){
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                settingsSpechLaung.equals(SystemCostant.Settings_SpeechLanguage)? Locale.getDefault() : settingsSpechLaung);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                spechStartText.setVisibility(View.VISIBLE);
                imageSpechVolume.setVisibility(View.VISIBLE);
                //Эта функция начинает запис текста
               spechStartText.setText(getString(R.string.spechToListen));
            }

            @Override
            public void onRmsChanged(float v) {
                if(v>7){
                    findViewById(R.id.imageSpechVolume).setAlpha(1);
                }else if(v>4 && v<7) {
                    findViewById(R.id.imageSpechVolume).setAlpha((float) 0.8);
                } else if(v>0 && v<4){
                    findViewById(R.id.imageSpechVolume).setAlpha((float) 0.5);
                } else if(v<0 ){
                    findViewById(R.id.imageSpechVolume).setAlpha((float) 0.0);
                }
            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {
                Log.d("xxx", String.valueOf(i));
               if(i==7){
                   spechStartText.setVisibility(View.GONE);
                   imageSpechVolume.setVisibility(View.GONE);
                   Toast.makeText(getApplicationContext(),
                           getString(R.string.emptySpeec),
                           Toast.LENGTH_SHORT).show();
               }
                if(i==2){
                    spechStartText.setVisibility(View.GONE);
                    imageSpechVolume.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.errorInternetConectSpeech),
                            Toast.LENGTH_LONG).show();
                    speechRecognizer.stopListening();
                }
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                saveSpeechToText(data);
                spechStartText.setVisibility(View.GONE);
            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }


    public void shareButton(View v){
        shareNotes(this, valueTextEdit().toString());
    }

    public void deleteButton(View v){
        fileCore.transferNotes(idNote,"trash",folder);
        closeNotesSave(false,false);
        Toast.makeText(getApplicationContext(),
                R.string.transferToTrash, Toast.LENGTH_LONG).show();
    }
}