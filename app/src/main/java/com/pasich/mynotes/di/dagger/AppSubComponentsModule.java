package com.pasich.mynotes.di.dagger;

import com.pasich.mynotes.base.dagger.ActivityComponentBuilder;
import com.pasich.mynotes.di.main.MainActivityComponent;
import com.pasich.mynotes.ui.view.activity.MainActivity;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {MainActivityComponent.class})
public class AppSubComponentsModule {

  @Provides
  @IntoMap
  @ClassKey(MainActivity.class)
  ActivityComponentBuilder provideSplashViewBuilder(MainActivityComponent.Builder builder) {
    return builder;
  }
}
