package com.pasich.mynotes.di.dagger;


import com.pasich.mynotes.ui.view.activity.NoteActivity;

import dagger.Component;

@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(NoteActivity noteActivity);
}
