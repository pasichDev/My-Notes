package com.pasich.mynotes.di.dagger;

import com.pasich.mynotes.di.ComponentsHolder;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, AppSubComponentsModule.class})
public interface AppComponent {
  void injectComponentsHolder(ComponentsHolder componentsHolder);
}
