package com.pasich.mynotes.injection.dagger;

import com.pasich.mynotes.injection.ComponentsHolder;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, AppSubComponentsModule.class})
public interface AppComponent {
  void injectComponentsHolder(ComponentsHolder componentsHolder);
}
