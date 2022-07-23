package com.pasich.mynotes.di.dagger;

import com.pasich.mynotes.base.dagger.ActivityComponentBuilder;
import com.pasich.mynotes.di.main.MainActivityComponent;
import com.pasich.mynotes.di.trash.TrashActivityComponent;
import com.pasich.mynotes.ui.view.activity.MainActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {MainActivityComponent.class, TrashActivityComponent.class})
public class AppSubComponentsModule {

  @Provides
  @IntoMap
  @ClassKey(MainActivity.class)
  ActivityComponentBuilder provideSplashViewBuilder(MainActivityComponent.Builder builder) {
    return builder;
  }

  @Provides
  @IntoMap
  @ClassKey(TrashActivity.class)
  ActivityComponentBuilder provideSplashViewBuilderTrash(TrashActivityComponent.Builder builder) {
    return builder;
  }
}
