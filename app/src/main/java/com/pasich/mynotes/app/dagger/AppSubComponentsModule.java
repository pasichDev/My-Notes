package com.pasich.mynotes.app.dagger;

import com.pasich.mynotes.base.dagger.ActivityComponentBuilder;
import com.pasich.mynotes.ui.view.main.MainActivity;
import com.pasich.mynotes.ui.view.main.dagger.MainActivityComponent;

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
