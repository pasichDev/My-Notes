package com.pasich.mynotes.di.note;

import com.pasich.mynotes.ui.view.activity.NoteActivity;

import dagger.Component;

@Component(modules = NoteActivityModule.class)
public interface NoteActivityComponent {
    void inject(NoteActivity activity);
}
