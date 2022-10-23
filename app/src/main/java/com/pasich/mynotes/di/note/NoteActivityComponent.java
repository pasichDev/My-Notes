package com.pasich.mynotes.di.note;

import com.pasich.mynotes.base.dagger.ActivityComponent;
import com.pasich.mynotes.base.dagger.ActivityComponentBuilder;
import com.pasich.mynotes.di.main.MainActivityScope;
import com.pasich.mynotes.ui.view.activity.NoteActivity;

import dagger.Subcomponent;

@MainActivityScope
@Subcomponent(modules = NoteActivityModule.class)
public interface NoteActivityComponent extends ActivityComponent<NoteActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<NoteActivityComponent, NoteActivityModule> {
    }
}