package com.pasich.mynotes.di.component;

import com.pasich.mynotes.di.module.ActivityModule;
import com.pasich.mynotes.di.module.MainActivityModule;
import com.pasich.mynotes.di.module.MoreNoteModule;
import com.pasich.mynotes.di.module.NoteActivityModule;
import com.pasich.mynotes.di.module.SearchDialogModule;
import com.pasich.mynotes.di.module.TrashActivityModule;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.helloUI.HelloActivity;
import com.pasich.mynotes.ui.view.activity.MainActivity;
import com.pasich.mynotes.ui.view.activity.NoteActivity;
import com.pasich.mynotes.ui.view.activity.NoteWidgetConfigureActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;
import com.pasich.mynotes.ui.view.dialogs.MoreNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.main.DeleteTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.NameTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.SearchDialog;
import com.pasich.mynotes.ui.view.dialogs.trash.CleanTrashDialog;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MainActivityModule.class, NoteActivityModule.class, SearchDialogModule.class, TrashActivityModule.class, MoreNoteModule.class})
public interface ActivityComponent {

    void inject(HelloActivity activity);

    void inject(MainActivity activity);

    void inject(NoteActivity activity);

    void inject(TrashActivity activity);

    void inject(NoteWidgetConfigureActivity activity);

    void inject(NameTagDialog dialog);


    void inject(DeleteTagDialog dialog);

    void inject(SearchDialog dialog);

    void inject(CleanTrashDialog dialog);

    void inject(MoreNoteDialog dialog);

}
