package com.pasich.mynotes.di.trash;

import com.pasich.mynotes.base.dagger.ActivityComponent;
import com.pasich.mynotes.base.dagger.ActivityComponentBuilder;
import com.pasich.mynotes.di.main.MainActivityScope;
import com.pasich.mynotes.ui.view.activity.TrashActivity;

import dagger.Subcomponent;

@MainActivityScope
@Subcomponent(modules = TrashActivityModule.class)
public interface TrashActivityComponent extends ActivityComponent<TrashActivity> {

  @Subcomponent.Builder
  interface Builder extends ActivityComponentBuilder<TrashActivityComponent, TrashActivityModule> {}
}
