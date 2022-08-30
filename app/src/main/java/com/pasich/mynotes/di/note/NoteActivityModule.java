package com.pasich.mynotes.di.note;


import com.pasich.mynotes.base.dagger.ActivityModule;
import com.pasich.mynotes.di.main.MainActivityScope;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.utils.activity.NoteUtils;
import com.pasich.mynotes.utils.permissionManager.PermissionManager;

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

    @MainActivityScope
    @Provides
    PermissionManager providerPermissionManager() {
        return new PermissionManager();
    }

    @MainActivityScope
    @Provides
    NoteUtils providerNoteUtils() {
        return new NoteUtils();
    }

}
