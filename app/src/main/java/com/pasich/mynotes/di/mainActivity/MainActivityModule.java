package com.pasich.mynotes.di.mainActivity;

import com.pasich.mynotes.utils.activity.MainUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Singleton
    @Provides
    public MainUtils providerBean() {
        return new MainUtils();
    }
}