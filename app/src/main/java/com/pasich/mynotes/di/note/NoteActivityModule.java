package com.pasich.mynotes.di.note;


import com.pasich.mynotes.base.dagger.ActivityModule;
import com.pasich.mynotes.di.main.MainActivityScope;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteActivityModule implements ActivityModule {

    public NoteActivityModule() {
    }


    @MainActivityScope
    @Provides
    NoteContract.presenter providerNotePresenter() {
        return new NotePresenter();
    }


}
