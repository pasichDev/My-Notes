package com.pasich.mynotes.di.note;

import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteActivityModule {

    @Provides
    public NoteContract.presenter providerPresenter() {
        return new NotePresenter();
    }


}