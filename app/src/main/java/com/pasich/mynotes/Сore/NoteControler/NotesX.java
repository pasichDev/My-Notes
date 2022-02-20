package com.pasich.mynotes.Сore.NoteControler;

import static android.speech.SpeechRecognizer.isRecognitionAvailable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class NotesX  {
    private final Context context;
    private final EditText editTextToActivity;
    private final ImageButton buttonEditText, SpechToTextButton;
    private final String KeyFunction;

    public NotesX(Context context, EditText editTextToActivity,
                  ImageButton buttonEditText,String keyFunction,ImageButton SpechToTextButton){
        this.context = context;
        this.KeyFunction = keyFunction;
        this.buttonEditText = buttonEditText;
        this.editTextToActivity = editTextToActivity;
        this.SpechToTextButton = SpechToTextButton;
    }


    /**
     * Метод который деактивирует окно изменения замтеки
     */
    public void deactiveEditText() {
        editTextToActivity.setEnabled(false);
        editTextToActivity.setFocusable(false);
        editTextToActivity.setFocusableInTouchMode(false);
        if(KeyFunction.equals("EditNote")) {
            buttonEditText.setVisibility(View.VISIBLE);
        }else if(KeyFunction.equals("TrashNotes")){
            buttonEditText.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Метод который активирует окно редактирования заметки
     */
    public void activeEditText() {
        editTextToActivity.setEnabled(true);
        editTextToActivity.setFocusable(true);
        editTextToActivity.setSelection(editTextToActivity.getText().length());
        editTextToActivity.setFocusableInTouchMode(true);
        editTextToActivity.requestFocus();
        buttonEditText.setVisibility(View.GONE);
        if(isRecognitionAvailable(context.getApplicationContext())){
            SpechToTextButton.setVisibility(View.VISIBLE);}
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextToActivity, InputMethodManager.SHOW_FORCED);
    }


    /**
     * Метод который изменяет размер шрифта окна редактирования заметки
     * @param sizeText - значение выбрано пользователем, от 14 - до 38
     */
    public void SetTextSize(int sizeText){
        sizeText = sizeText==0 ? 20 : sizeText;
        if(sizeText>=14|| sizeText<=38) editTextToActivity.setTextSize(sizeText);
    }

    /**
     * Метод который изменяет стиль текста окна редактирования заметки
     * Bold,Italic,Normal,Italic-Bold
     * @param style - стиль который выбран в настройках
     */
    public void setStyleText(String style){
        switch (style){
            case "normal":
                editTextToActivity.setTypeface(null, Typeface.NORMAL);
               break;
            case "italic":
                editTextToActivity.setTypeface(null, Typeface.ITALIC);
                break;
            case "bold":
                editTextToActivity.setTypeface(null, Typeface.BOLD);
                break;
            case "bold-italic":
                editTextToActivity.setTypeface(null, Typeface.BOLD_ITALIC);
                break;
            default:

                break;
        }

    }

    public static void closeKeyboard(Activity activity){
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }



}
