package com.pasich.mynotes.app.dagger;

import com.pasich.mynotes.app.ComponentsHolder;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, AppSubComponentsModule.class})
public interface AppComponent {
  void injectComponentsHolder(ComponentsHolder componentsHolder);
}
