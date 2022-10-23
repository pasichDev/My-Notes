package com.pasich.mynotes.di.trash;

import com.pasich.mynotes.ui.view.activity.TrashActivity;

import dagger.Component;

@Component(modules = TrashActivityModule.class)
public interface TrashActivityComponent {
  void inject(TrashActivity activity);
}
