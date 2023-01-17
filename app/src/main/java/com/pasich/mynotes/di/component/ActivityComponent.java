package com.pasich.mynotes.di.component;

import com.pasich.mynotes.di.module.ActivityModule;
import com.pasich.mynotes.di.module.BindingModule;
import com.pasich.mynotes.di.module.ListUtilsModule;
import com.pasich.mynotes.di.module.OtherUtilsModule;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.view.activity.AboutActivity;
import com.pasich.mynotes.ui.view.activity.BackupActivity;
import com.pasich.mynotes.ui.view.activity.MainActivity;
import com.pasich.mynotes.ui.view.activity.NoteActivity;
import com.pasich.mynotes.ui.view.activity.ThemeActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;
import com.pasich.mynotes.ui.view.dialogs.MoreNoteDialog;
import com.pasich.mynotes.ui.view.dialogs.main.DeleteTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.NameTagDialog;
import com.pasich.mynotes.ui.view.dialogs.main.SearchDialog;
import com.pasich.mynotes.ui.view.dialogs.settings.aboutDialog.AboutDialog;
import com.pasich.mynotes.ui.view.dialogs.trash.CleanTrashDialog;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, BindingModule.class, ListUtilsModule.class, OtherUtilsModule.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(NoteActivity activity);

    void inject(AboutActivity activity);

    void inject(BackupActivity activity);

    void inject(TrashActivity activity);

    void inject(ThemeActivity activity);

    void inject(NameTagDialog dialog);

    void inject(DeleteTagDialog dialog);

    void inject(SearchDialog dialog);

    void inject(CleanTrashDialog dialog);

    void inject(MoreNoteDialog dialog);

    void inject(AboutDialog dialog);
}
