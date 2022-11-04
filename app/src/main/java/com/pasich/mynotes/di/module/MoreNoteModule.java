package com.pasich.mynotes.di.module;


import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.utils.prefences.TextStylePreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class MoreNoteModule {


    @Provides
    @PerActivity
    TextStylePreferences providesTextStyle() {
        return new TextStylePreferences();
    }

}
