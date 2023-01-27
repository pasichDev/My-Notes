package com.pasich.mynotes.di.activity;

import com.pasich.mynotes.utils.tool.TextStyleTool;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public class OtherUtilsModule {


    @Provides
    @ActivityScoped
    TextStyleTool providesTextStyle() {
        return new TextStyleTool();
    }

}
