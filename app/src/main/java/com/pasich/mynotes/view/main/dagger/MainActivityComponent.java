package com.pasich.mynotes.view.main.dagger;

import com.pasich.mynotes.base.dagger.ActivityComponent;
import com.pasich.mynotes.base.dagger.ActivityComponentBuilder;
import com.pasich.mynotes.view.main.MainActivity;

import dagger.Subcomponent;

@MainActivityScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent extends ActivityComponent<MainActivity> {

  @Subcomponent.Builder
  interface Builder extends ActivityComponentBuilder<MainActivityComponent, MainActivityModule> {}
}
