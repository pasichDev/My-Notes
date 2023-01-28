package com.pasich.mynotes.di.activity;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.pasich.mynotes.ui.contract.BackupContract;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.ui.contract.dialogs.ClearTrashDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.DeleteTagDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.NewTagDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.SearchDialogContract;
import com.pasich.mynotes.ui.presenter.BackupPresenter;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.ui.presenter.TrashPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.ClearTrashDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.DeleteTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.NameTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.SearchDialogPresenter;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.rx.AppSchedulerProvider;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;
import io.reactivex.disposables.CompositeDisposable;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {


    @Provides
    @ActivityScoped
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @ActivityScoped
    SchedulerProvider provideScheduleProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @ActivityScoped
    MainContract.presenter providesMainPresenter(MainPresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityScoped
    BackupContract.presenter providesBackupPresenter(BackupPresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityScoped
    NoteContract.presenter providesNotePresenter(NotePresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityScoped
    TrashContract.presenter providerTrashActivityPresenter(TrashPresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityScoped
    NewTagDialogContract.presenter providerNewTagDialogPresenter(NameTagDialogPresenter presenter) {
        return presenter;

    }


    @Provides
    @ActivityScoped
    DeleteTagDialogContract.presenter providerDeleteTagDialogPresenter(DeleteTagDialogPresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityScoped
    SearchDialogContract.presenter providerSearchDialogPresenter(SearchDialogPresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityScoped
    ClearTrashDialogContract.presenter providerClearTrashDialogPresenter(ClearTrashDialogPresenter presenter) {
        return presenter;
    }


    @Named("ActionUtilsTrash")
    @Provides
    @ActivityScoped
    ActionUtils providerActionUtilsTrash(@Named("TrashActivityRootLayout") CoordinatorLayout coordinatorLayout) {
        return new ActionUtils(coordinatorLayout);
    }

    @Named("ActionUtilsMain")
    @Provides
    @ActivityScoped
    ActionUtils providerActionUtilsMain(@Named("MainActivityRootLayout") CoordinatorLayout coordinatorLayout) {
        return new ActionUtils(coordinatorLayout);
    }
}
