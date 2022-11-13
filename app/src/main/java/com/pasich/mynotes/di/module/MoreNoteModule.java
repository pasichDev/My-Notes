package com.pasich.mynotes.di.module;


import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.utils.tool.TextStyleTool;

import dagger.Module;
import dagger.Provides;

@Module
public class MoreNoteModule {


    @Provides
    @PerActivity
    TextStyleTool providesTextStyle() {
        return new TextStyleTool();
    }

}
