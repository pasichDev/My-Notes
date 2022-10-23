package com.pasich.mynotes.di.main;

import com.pasich.mynotes.ui.view.activity.MainActivity;

import dagger.Component;

@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
  void inject(MainActivity activity);
}
