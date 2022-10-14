package com.pasich.mynotes.di.dagger;

import com.pasich.mynotes.di.ComponentsHolder;

import dagger.Component;

@AppScope
@Component(modules = {MainModule.class, MainComponent.class})
public interface DaggerMainComponent {
  void builder(ComponentsHolder componentsHolder);
}
