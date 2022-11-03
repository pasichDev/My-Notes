package com.pasich.mynotes.di.module;


import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.pasich.mynotes.di.scope.ActivityContext;
import com.pasich.mynotes.di.scope.PerActivity;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteActivityModule {


    @Provides
    @PerActivity
    SpeechRecognizer providerRecordIntent(@ActivityContext Context context) {
        return SpeechRecognizer.createSpeechRecognizer(context);
    }

    @Provides
    @PerActivity
    Intent providerSpeechRecognizerIntent() {
        return new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                .putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);

    }
}
