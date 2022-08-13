package com.pasich.mynotes.utils.simplifications;


import android.os.Bundle;
import android.speech.RecognitionListener;


public abstract class SpeechRecognizer implements RecognitionListener {


    public abstract void startListener();

    public abstract void errorDebug(int i);

    public abstract void saveText(Bundle bundle);

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        startListener();
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float v) {
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int i) {
        errorDebug(i);
    }

    @Override
    public void onResults(Bundle bundle) {
        saveText(bundle);
    }

    @Override
    public void onPartialResults(Bundle bundle) {
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
    }
}







