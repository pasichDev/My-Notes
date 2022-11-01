package com.pasich.mynotes.di.component;

import com.pasich.mynotes.di.module.ActivityModule;
import com.pasich.mynotes.di.module.MainActivityModule;
import com.pasich.mynotes.di.module.NoteActivityModule;
import com.pasich.mynotes.di.module.SearchDialogModule;
import com.pasich.mynotes.di.module.TrashActivityModule;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.view.activity.MainActivity;
import com.pasich.mynotes.ui.view.activity.NoteActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;
import com.pasich.mynotes.ui.view.dialogs.main.ChoiceTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.DeleteTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.NewTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.SearchDialog;
import com.pasich.mynotes.ui.view.dialogs.trash.CleanTrashDialog;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, MainActivityModule.class, NoteActivityModule.class,
                SearchDialogModule.class, TrashActivityModule.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(NoteActivity activity);

    void inject(TrashActivity activity);

    void inject(NewTagDialog dialog);

    void inject(ChoiceTagDialog dialog);

    void inject(DeleteTagDialog dialog);

    void inject(SearchDialog dialog);

    void inject(CleanTrashDialog dialog);
}
