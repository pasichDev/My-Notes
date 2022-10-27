package com.pasich.mynotes.di.component;

import com.pasich.mynotes.di.module.ActivityModule;
import com.pasich.mynotes.di.module.MainActivityModule;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.view.activity.MainActivity;
import com.pasich.mynotes.ui.view.activity.NoteActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MainActivityModule.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(NoteActivity activity);

    void inject(TrashActivity activity);


}
